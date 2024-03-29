package com.devspark.userservice.pojo.vo.matchedService.getRecommendApi;

import lombok.Data;

import java.util.List;

@Data
public class GetRecomUserListVO {
    private List<RecommendedUser> recommendedUserList;
}
