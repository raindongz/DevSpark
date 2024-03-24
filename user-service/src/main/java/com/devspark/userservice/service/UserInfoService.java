package com.devspark.userservice.service;

import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.dto.UserListQueryDTO;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;
import java.util.List;

public interface UserInfoService {
    GetRecomUserListVO getMyRecommendUserList(GetRecomUserListDTO getRecomUserListDTO);

    List<UserInfoEntity> getAllUserInfo();

    UserInfoEntity saveUserInfo(UserInfoEntity userInfoEntity);

    List<UserInfoEntity> getUserList(UserListQueryDTO userListQueryDTO);
}
