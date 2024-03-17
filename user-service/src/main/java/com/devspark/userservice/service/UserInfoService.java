package com.devspark.userservice.service;

import com.devspark.userservice.pojo.dto.CreateUserDTO;
import com.devspark.userservice.pojo.dto.LoginUserDTO;
import com.devspark.userservice.pojo.vo.CreateUserVO;
import com.devspark.userservice.pojo.vo.LoginUserVO;

public interface UserInfoService {
    CreateUserVO createUser(CreateUserDTO request);
    LoginUserVO loginUser(LoginUserDTO request);
}
