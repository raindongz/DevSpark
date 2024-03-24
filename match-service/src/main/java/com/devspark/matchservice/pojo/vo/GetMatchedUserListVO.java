package com.devspark.matchservice.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMatchedUserListVO {
    private List<MatchedUserInfo> matchedUserInfoList;
}
