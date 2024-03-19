package com.devspark.userservice.repository;

import com.devspark.userservice.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
}
