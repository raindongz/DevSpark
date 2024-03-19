package com.devspark.userservice.service.impl;

import com.devspark.userservice.constants.ScoreLowerAndUpperBound;
import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.exception.customExceptions.UserNotFoundException;
import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.mapper.UserInfoMapper;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;
import com.devspark.userservice.pojo.vo.RecommendedUser;
import com.devspark.userservice.repository.UserInfoRepository;
import com.devspark.userservice.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
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
        Optional<UserInfoEntity> userInfo = userInfoRepository.getUserInfoEntityByUserId(getRecomUserListDTO.getUserId());
        if (userInfo.isEmpty()){
            log.error("user not exist: " + getRecomUserListDTO.getUserId());
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
}
