package com.devspark.authservice.pojo.mapper;

import com.devspark.authservice.entity.AuthEntity;
import com.devspark.authservice.pojo.dto.CreateUserDTO;
import com.devspark.authservice.pojo.dto.CreateUserProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    CreateUserProfileDTO userDTOToUserProfile(CreateUserDTO createUserDTO);

    AuthEntity userDTOToAuthEntity(CreateUserDTO createUserDTO);
}
