package com.devspark.authservice.service;

import com.devspark.authservice.pojo.dto.CreateUserDTO;
import com.devspark.authservice.pojo.dto.LoginUserDTO;
import com.devspark.authservice.pojo.vo.CreateUserVO;
import com.devspark.authservice.pojo.vo.LoginUserVO;

public interface AuthService {
    CreateUserVO createUser(CreateUserDTO request);
    LoginUserVO loginUser(LoginUserDTO request);
}
