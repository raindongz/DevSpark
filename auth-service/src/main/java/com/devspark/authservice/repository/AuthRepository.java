package com.devspark.authservice.repository;

import com.devspark.authservice.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<UserInfoEntity, Long> {

    Optional<UserInfoEntity> getUserInfoEntityByUsername(String username);
}
