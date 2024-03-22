package com.devspark.userservice.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserListQueryDTO {
    private List<Long> userIds;
    private String username;
    private String email;
    private String phone;
    private String gender;

    private Integer page;
    private Integer pageSize;
    private String orderBy;
    private String direction;
}
