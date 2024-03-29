package com.devspark.userservice.service;

import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.dto.UserListQueryDTO;
import com.devspark.userservice.pojo.vo.matchedService.getMatchedListApi.MatchedUserInfo;
import com.devspark.userservice.pojo.vo.matchedService.getRecommendApi.GetRecomUserListVO;
import java.util.List;

public interface UserInfoService {
    GetRecomUserListVO getMyRecommendUserList(GetRecomUserListDTO getRecomUserListDTO);

    UserInfoEntity getMyProfile(Long userId);

    UserInfoEntity saveUserInfo(UserInfoEntity userInfoEntity);

    List<MatchedUserInfo> getUserList(UserListQueryDTO userListQueryDTO);
}
