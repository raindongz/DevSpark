package com.devspark.userservice.service;

import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;

public interface UserInfoService {
    GetRecomUserListVO getMyRecommendUserList(GetRecomUserListDTO getRecomUserListDTO);
}
