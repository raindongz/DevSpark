package com.devspark.userservice.pojo.mapper;

import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.pojo.dto.CreateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfoEntity userDTOToEntity(CreateUserDTO createUserDTO);
}
