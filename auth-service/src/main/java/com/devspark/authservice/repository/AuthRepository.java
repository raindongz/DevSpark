package com.devspark.authservice.repository;

import com.devspark.authservice.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {

    Optional<AuthEntity> getUserInfoEntityByUsername(String username);
}
