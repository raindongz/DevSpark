package com.devspark.userservice.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RecommendedUser {

    private Long userId;
    private String username;
    private Integer gender;
    private Integer age;
    private String avatars;
    private Integer jobTitle;
    private Integer favLanguage;
    private String favFramework;
    private String githubUrl;
    private Integer mbtiType;
    private String technicalTags;
    private String nonTechTags;
    private String selfDescription;
    private Integer totalScore;
    private Date createdAt;
}
