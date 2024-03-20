package com.devspark.userservice.service;

import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;
import com.devspark.userservice.entity.UserInfoEntity;
import java.util.List;

public interface UserInfoService {
    GetRecomUserListVO getMyRecommendUserList(GetRecomUserListDTO getRecomUserListDTO);
    List<UserInfoEntity> getAllUserInfo();
}
