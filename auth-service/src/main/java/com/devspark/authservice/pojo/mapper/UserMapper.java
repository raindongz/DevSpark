package com.devspark.authservice.pojo.mapper;

import com.devspark.authservice.entity.UserInfoEntity;
import com.devspark.authservice.pojo.dto.CreateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfoEntity userDTOToEntity(CreateUserDTO createUserDTO);
}
