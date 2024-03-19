package com.devspark.userservice.pojo.mapper;

import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.pojo.vo.RecommendedUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    RecommendedUser userEntityToVO(UserInfoEntity userInfo);
}
