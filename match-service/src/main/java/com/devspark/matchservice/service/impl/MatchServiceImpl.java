package com.devspark.matchservice.service.impl;

import com.devspark.matchservice.entity.LikeHistoryEntity;
import com.devspark.matchservice.exception.customExceptions.NoRecommendUserException;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import com.devspark.matchservice.pojo.vo.RecommendedUser;
import com.devspark.matchservice.repository.LikeHistoryRepository;
import com.devspark.matchservice.service.MatchService;
import com.devspark.matchservice.service.feignClients.APIClientForUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    private final LikeHistoryRepository likeHistoryRepository;
    private final APIClientForUserService apiClientForUserService;
    public MatchServiceImpl(LikeHistoryRepository likeHistoryRepository, APIClientForUserService apiClientForUserService) {
        this.likeHistoryRepository = likeHistoryRepository;
        this.apiClientForUserService = apiClientForUserService;
    }

    @Override
    public GetRecomUserListVO getMyRecommendUserList(GetMyRecommendUserListDTO getMyRecommendUserListDTO) {
        // 1. call user service to get the recommended list
        GetRecomUserListVO getRecomUserListVO = apiClientForUserService.getRecommendUserList(getMyRecommendUserListDTO);
        Optional<List<RecommendedUser>> recommendedUser = Optional.ofNullable(getRecomUserListVO.getRecommendedUserList());
        if (recommendedUser.isEmpty()){
            log.error("no recommend user for: "+getMyRecommendUserListDTO.getUserId());
            throw new NoRecommendUserException("no recommended user found");
        }

        // 2. get all the users who liked me, convert to set of user ids
        Set<Long> idsOfUserWhoLikedMe = likeHistoryRepository
                .getAllByPersonBeLiked(getMyRecommendUserListDTO.getUserId())
                .stream()
                .map(LikeHistoryEntity::getPersonClickLike)
                .collect(Collectors.toSet());

        // 3. iterate the recommended list, for each recommended user,
        //      check if he/she appears in map, if yes, set likeMe to true
        getRecomUserListVO.getRecommendedUserList().forEach(
                each -> each.setLikeMe(idsOfUserWhoLikedMe.contains(each.getUserId())));

        // 4. return the recommended list
        return getRecomUserListVO;
    }
}
