package com.devspark.userservice.service.impl;

import com.devspark.userservice.constants.DeleteFlags;
import com.devspark.userservice.constants.ScoreLowerAndUpperBound;
import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.exception.customExceptions.UserNotFoundException;
import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.dto.UserListQueryDTO;
import com.devspark.userservice.pojo.mapper.UserInfoMapper;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;
import com.devspark.userservice.pojo.vo.RecommendedUser;
import com.devspark.userservice.repository.UserInfoRepository;
import com.devspark.userservice.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public GetRecomUserListVO getMyRecommendUserList(GetRecomUserListDTO getRecomUserListDTO) {
        // 1. get my score
        Optional<UserInfoEntity> userInfo = userInfoRepository.getUserInfoEntityByUserIdAndDeletedFlag(getRecomUserListDTO.getUserId(), DeleteFlags.NOT_DELETED);
        if (userInfo.isEmpty()){
            log.error("user not exist, userid: " + getRecomUserListDTO.getUserId());
            throw new UserNotFoundException("user not exist");
        }

        // 2. get user list with similar scores(by filter all user within a score range)
        List<UserInfoEntity> userInfoListByScoreRange = userInfoRepository.getUserInfoListByScoreRange(
                userInfo.get().getTotalScore() - ScoreLowerAndUpperBound.LOWER_BOUND,
                userInfo.get().getTotalScore() + ScoreLowerAndUpperBound.UPPER_BOUND
        );

        // 3. construct VO list and copy beans
        GetRecomUserListVO getRecomUserListVO = new GetRecomUserListVO();
        List<RecommendedUser> listOfRecommendUser = userInfoListByScoreRange
                .stream()
                .map(UserInfoMapper.INSTANCE::userEntityToVO)
                .toList();

        getRecomUserListVO.setRecommendedUsers(listOfRecommendUser);

        return getRecomUserListVO;
    }

    @Override
    public List<UserInfoEntity> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public List<UserInfoEntity> getUserList(UserListQueryDTO userListQueryDTO) {
        // Default not pageable
        Pageable pageable = Pageable.unpaged();
        // If page and pageSize are not null, then pageable
        // Example: page = 1, pageSize = 10
        if (userListQueryDTO.getPage() != null && userListQueryDTO.getPageSize() != null) {
            // If orderBy and direction are not null, then sort
            // Example: orderBy = "id", direction = "DESC"
            if (userListQueryDTO.getOrderBy() != null && userListQueryDTO.getDirection() != null
            && !userListQueryDTO.getOrderBy().isEmpty() && !userListQueryDTO.getDirection().isEmpty()){
                Sort sort = Sort.by(userListQueryDTO.getDirection().equals("ASC") ?
                        Sort.Direction.ASC : Sort.Direction.DESC,
                        userListQueryDTO.getOrderBy());
                pageable = PageRequest.of(userListQueryDTO.getPage() - 1, userListQueryDTO.getPageSize(), sort);
            }else
                pageable = PageRequest.of(userListQueryDTO.getPage() - 1, userListQueryDTO.getPageSize());
        }
        return userInfoRepository.getUserList(userListQueryDTO.getUserIds(), pageable);
    }
}
