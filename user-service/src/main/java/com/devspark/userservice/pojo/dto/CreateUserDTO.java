package com.devspark.userservice.pojo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotNull(message = "username cannot be empty")
    @Size(min = 2, max = 15)
    private String username;

    @NotNull(message = "password cannot be empty")
    private String hashedPassword;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be valid email address")
    private String email;

    @NotNull(message = "gender can not be null")
    private Integer gender;

    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "age must be a number")
    @Min(value = 18, message = "age must be over 18")
    private Integer age;

    @NotNull(message = "job title can not be null")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "job title must be a number")
    @Min(value = 1, message = "job title number must be greater than 1")
    private Integer jobTitle;

    @NotNull(message = "favourite language can not be null")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "fav language must be a number")
    @Min(value = 1, message = "fav language number must be greater than 1")
    private Integer favLanguage;

    private String phone;
    private String avatars;
    private String favFramework;
    private String githubUrl;
    private Integer mbtiType;
    private String technicalTags;
    private String nonTechTags;
    private String selfDescription;

}
