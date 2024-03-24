package com.devspark.matchservice.service;

import com.devspark.matchservice.pojo.dto.GetMyMatchedUserListDTO;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.dto.LikeOrUnlikeDTO;
import com.devspark.matchservice.pojo.vo.GetMatchedUserListVO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import com.devspark.matchservice.pojo.vo.LikeOrUnlikeVO;

public interface MatchService {
    GetRecomUserListVO getMyRecommendUserList(Long userId);
    GetMatchedUserListVO getMyMatchedUserList(Long userId);

    LikeOrUnlikeVO likeOrUnlike(Long userId, LikeOrUnlikeDTO likeOrUnlikeDTO);
}
