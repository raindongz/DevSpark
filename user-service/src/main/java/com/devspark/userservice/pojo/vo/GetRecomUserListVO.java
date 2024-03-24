package com.devspark.userservice.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class GetRecomUserListVO {
    private List<RecommendedUser> recommendedUsers;
}
