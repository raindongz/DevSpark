package com.devspark.userservice.repository;

import com.devspark.userservice.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

    Optional<UserInfoEntity> getUserInfoEntityByUsername(String username);
}
