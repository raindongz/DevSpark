package com.devspark.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * user information entity
 * */

@Data
@Entity
@Table(name = "tb_users_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL", name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 25)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "avatars", length = 2048)
    private String avatars;

    @Column(name = "job_title", nullable = false)
    private Integer jobTitle;

    @Column(name = "fav_language", nullable = false)
    private Integer favLanguage;

    @Column(name = "fav_framework", length = 10)
    private String favFramework;

    @Column(name = "github_url", length = 50)
    private String githubUrl;

    @Column(name = "mbti_type")
    private Integer mbtiType;

    @Column(name = "technical_tags", length = 40)
    private String technicalTags;

    @Column(name = "non_tech_tags", length = 20)
    private String nonTechTags;

    @Column(name = "self_description", length = 200)
    private String selfDescription;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "deleted_flag", nullable = false)
    private Integer deletedFlag;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

}
