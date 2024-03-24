package com.devspark.authservice.controller;

import com.devspark.authservice.pojo.dto.CreateUserDTO;
import com.devspark.authservice.pojo.dto.LoginUserDTO;
import com.devspark.authservice.pojo.vo.CreateUserVO;
import com.devspark.authservice.pojo.vo.LoginUserVO;
import com.devspark.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserVO> createUser(@RequestBody @Valid CreateUserDTO userInfoDTO){
        CreateUserVO response = authService.createUser(userInfoDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserVO> login(@RequestBody @Valid LoginUserDTO loginUserDTO){
        LoginUserVO response = authService.loginUser(loginUserDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
