package com.devspark.authservice.pojo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class CreateUserProfileDTO {

    private Long userId;
    private String username;
    private String email;
    private Integer gender;
    private Integer age;
    private Integer jobTitle;
    private Integer favLanguage;
    private String phone;
    private String avatars;
    private String favFramework;
    private String githubUrl;
    private Integer mbtiType;
    private String technicalTags;
    private String nonTechTags;
    private String selfDescription;

    private Integer deletedFlag;
    private Date createdAt;
    private Date updatedAt;
    private Integer totalScore;
}
