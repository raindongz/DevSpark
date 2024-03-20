package com.devspark.matchservice.service;

import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;

public interface MatchService {
    GetRecomUserListVO getMyRecommendUserList(GetMyRecommendUserListDTO getMyRecommendUserListDTO);
}
