package com.devspark.userservice.pojo.vo.matchedService.getMatchedListApi;

import lombok.Data;

import java.util.Date;

@Data
public class MatchedUserInfo {
    private Long userId;
    private String username;
    private Integer gender;
    private Integer age;
    private String avatars;
    private Integer jobTitle;
    private Integer favLanguage;
    private String selfDescription;
    private Date createdAt;
}
