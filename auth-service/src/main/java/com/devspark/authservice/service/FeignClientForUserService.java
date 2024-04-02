package com.devspark.authservice.service;

import com.devspark.authservice.pojo.dto.CreateUserProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(url = "http://localhost:8082", value = "user-service")
@FeignClient("user-service")
public interface FeignClientForUserService {
    @PostMapping("/api/user-info/save-user-info")
    CreateUserProfileDTO saveUserInfo(@RequestBody CreateUserProfileDTO createUserProfileDTO);
}
