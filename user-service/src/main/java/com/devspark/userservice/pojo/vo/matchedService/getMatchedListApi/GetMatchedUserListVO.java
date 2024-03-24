package com.devspark.userservice.pojo.vo.matchedService.getMatchedListApi;

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
