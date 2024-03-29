package com.devspark.matchservice.service.impl;

import com.devspark.matchservice.entity.LikeHistoryEntity;
import com.devspark.matchservice.entity.MatchHistoryEntity;
import com.devspark.matchservice.exception.customExceptions.LikeOrUnlikeException;
import com.devspark.matchservice.exception.customExceptions.NoRecommendUserException;
import com.devspark.matchservice.pojo.dto.GetMyMatchedUserListDTO;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.dto.LikeOrUnlikeDTO;
import com.devspark.matchservice.pojo.vo.*;
import com.devspark.matchservice.repository.LikeHistoryRepository;
import com.devspark.matchservice.repository.MatchedHistoryRepository;
import com.devspark.matchservice.service.MatchService;
import com.devspark.matchservice.service.feignClients.APIClientForUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    private final LikeHistoryRepository likeHistoryRepository;
    private final MatchedHistoryRepository matchedHistoryRepository;
    private final APIClientForUserService apiClientForUserService;


    public MatchServiceImpl(LikeHistoryRepository likeHistoryRepository, MatchedHistoryRepository matchedHistoryRepository, APIClientForUserService apiClientForUserService) {
        this.likeHistoryRepository = likeHistoryRepository;
        this.matchedHistoryRepository = matchedHistoryRepository;
        this.apiClientForUserService = apiClientForUserService;
    }

    @Override
    public GetRecomUserListVO getMyRecommendUserList(Long userId) {
        // 1. call user service to get the recommended list
        GetRecomUserListVO getRecomUserListVO = apiClientForUserService.getRecommendUserList(new GetMyRecommendUserListDTO(userId));
        Optional<List<RecommendedUser>> recommendedUser = Optional.ofNullable(getRecomUserListVO.getRecommendedUserList());
        if (recommendedUser.isEmpty()){
            log.error("no recommend user for: "+userId);
            throw new NoRecommendUserException("no recommended user found");
        }

        // 2. get all the users who liked me, convert to set of user ids
        Set<Long> idsOfUserWhoLikedMe = likeHistoryRepository
                .findAllByPersonBeLiked(userId)
                .stream()
                .map(LikeHistoryEntity::getPersonClickLiked)
                .collect(Collectors.toSet());

        // 3. iterate the recommended list, for each recommended user,
        //      check if he/she appears in map, if yes, set likeMe to true
        getRecomUserListVO.getRecommendedUserList().forEach(
                each -> each.setLikeMe(idsOfUserWhoLikedMe.contains(each.getUserId())));

        // 4. return the recommended list
        return getRecomUserListVO;
    }

    @Override
    public GetMatchedUserListVO getMyMatchedUserList(Long userId) {
        // 1. get all matched ids from db
        List<Long> matchedIdsPartOne = matchedHistoryRepository.findAllByMatchIdOne(userId)
                .stream()
                .map(MatchHistoryEntity::getMatchIdTwo)
                .toList();
        List<Long> matchedIdsPartTwo = matchedHistoryRepository.findAllByMatchIdTwo(userId)
                .stream()
                .map(MatchHistoryEntity::getMatchIdOne)
                .toList();
        ArrayList<Long> allUserIds = new ArrayList<>();
        allUserIds.addAll(matchedIdsPartOne);
        allUserIds.addAll(matchedIdsPartTwo);

        // 2. call user service with matched ids and get list of corresponding user info
        List<MatchedUserInfo> matchedUserInfoList = apiClientForUserService.getMatchedUserInfoList(new GetMyMatchedUserListDTO(allUserIds));
        return new GetMatchedUserListVO(matchedUserInfoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LikeOrUnlikeVO likeOrUnlike(Long userId, LikeOrUnlikeDTO likeOrUnlikeDTO) {
        // 1. check if it is a like or dislike operation
        Set<Long> userIdListILiked = likeHistoryRepository.findAllByPersonClickLiked(userId)
                .stream()
                .map(LikeHistoryEntity::getPersonBeLiked).collect(Collectors.toSet());
        boolean contains = userIdListILiked.contains(likeOrUnlikeDTO.getPersonILiked());

        // 2. if it is dislike operation and we haven't matched before, simply delete record from the like table.
        if (contains){
            try {
                likeHistoryRepository.deleteByPersonClickLikedAndPersonBeLiked(userId, likeOrUnlikeDTO.getPersonILiked());
            }catch (Exception e){
                log.error("delete like record error: person click like: "+userId+" person be liked: "+ likeOrUnlikeDTO.getPersonILiked() + e);
                throw new LikeOrUnlikeException("error happened, try again later");
            }
            return new LikeOrUnlikeVO(true);
        }

        // 3. if it is unlike operation, and, if we matched before
        //  delete the matched record, and insert the other user id(to people who click like)
        //  into like table
        Optional<MatchHistoryEntity> matchRecordPartOne = matchedHistoryRepository
                .findByMatchIdOneAndMatchIdTwo(userId, likeOrUnlikeDTO.getPersonILiked());
        if (matchRecordPartOne.isPresent()){
            try {
                matchedHistoryRepository.deleteByMatchIdOneAndMatchIdTwo(userId, likeOrUnlikeDTO.getPersonILiked());
            }catch (Exception e){
                log.error("delete match record error: match record one: "+userId+" match record two: "+ likeOrUnlikeDTO.getPersonILiked() + e);
                throw new LikeOrUnlikeException("error happened, try again later");
            }
            LikeHistoryEntity likeHistoryEntity = new LikeHistoryEntity();
            likeHistoryEntity.setPersonBeLiked(userId);
            likeHistoryEntity.setPersonClickLiked(likeOrUnlikeDTO.getPersonILiked());
            likeHistoryEntity.setCreateDate(new Date());
            try{
                likeHistoryRepository.save(likeHistoryEntity);
            }catch (Exception e){
                log.error("save like history error");
                throw new LikeOrUnlikeException("error happened, try again later");
            }
            return new LikeOrUnlikeVO(true);
        }

        Optional<MatchHistoryEntity> matchRecordPartTwo = matchedHistoryRepository
                .findByMatchIdOneAndMatchIdTwo(likeOrUnlikeDTO.getPersonILiked(), userId);
        if (matchRecordPartTwo.isPresent()){
            try {
                matchedHistoryRepository.deleteByMatchIdOneAndMatchIdTwo(likeOrUnlikeDTO.getPersonILiked(), userId);
            }catch (Exception e){
                log.error("delete match record error: match record one: "+userId+" match record two: "+ likeOrUnlikeDTO.getPersonILiked() + e);
                throw new LikeOrUnlikeException("error happened, try again later");
            }
            LikeHistoryEntity likeHistoryEntity = new LikeHistoryEntity();
            likeHistoryEntity.setPersonBeLiked(userId);
            likeHistoryEntity.setPersonClickLiked(likeOrUnlikeDTO.getPersonILiked());
            likeHistoryEntity.setCreateDate(new Date());
            try {
                likeHistoryRepository.save(likeHistoryEntity);
            }catch (Exception e){
                log.error("\"delete match record error: match record one: \"+userId+\" match record two: \"+ likeOrUnlikeDTO.getPersonILiked() + e");
                throw new LikeOrUnlikeException("error happend, try again later");
            }
            return new LikeOrUnlikeVO(true);
        }

        // 4. if it is like operation and the user I am liking also liked me
        //  remove the like record from like table and insert a new match record
        //  to matched record table
        Set<Long> userIdsLikedMe = likeHistoryRepository.findAllByPersonBeLiked(userId)
                .stream()
                .map(LikeHistoryEntity::getPersonClickLiked)
                .collect(Collectors.toSet());
        boolean likedMe = userIdsLikedMe.contains(likeOrUnlikeDTO.getPersonILiked());
        if (likedMe){
            likeHistoryRepository.deleteByPersonClickLikedAndPersonBeLiked(likeOrUnlikeDTO.getPersonILiked(), userId);
            MatchHistoryEntity matchHistoryEntity = new MatchHistoryEntity();
            matchHistoryEntity.setMatchIdOne(userId);
            matchHistoryEntity.setMatchIdTwo(likeOrUnlikeDTO.getPersonILiked());
            matchHistoryEntity.setMatchTime(new Date());
            matchedHistoryRepository.save(matchHistoryEntity);
            return new LikeOrUnlikeVO(true);
        }

        // 5. if it is a like operation and the user I am liking does not like me.
        //  put my id to person_who_click like
        //  and put another id to person_be_like then save to like table
        LikeHistoryEntity likeHistoryEntity = new LikeHistoryEntity();
        likeHistoryEntity.setCreateDate(new Date());
        likeHistoryEntity.setPersonBeLiked(likeOrUnlikeDTO.getPersonILiked());
        likeHistoryEntity.setPersonClickLiked(userId);
        likeHistoryRepository.save(likeHistoryEntity);

        // 6. return true if success, otherwise false;
        return new LikeOrUnlikeVO(true);
    }
}
