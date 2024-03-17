package com.devspark.userservice.controller;

import com.devspark.userservice.pojo.dto.CreateUserDTO;
import com.devspark.userservice.pojo.dto.LoginUserDTO;
import com.devspark.userservice.pojo.vo.CreateUserVO;
import com.devspark.userservice.pojo.vo.LoginUserVO;
import com.devspark.userservice.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserVO> createUser(@RequestBody @Valid CreateUserDTO userInfoDTO){
        CreateUserVO response = userInfoService.createUser(userInfoDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserVO> login(@RequestBody @Valid LoginUserDTO loginUserDTO){
        LoginUserVO response = userInfoService.loginUser(loginUserDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
