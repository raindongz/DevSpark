package com.devspark.userservice.repository;

import com.devspark.userservice.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

    @Query("SELECT u FROM UserInfoEntity u WHERE u.totalScore BETWEEN :min AND :max AND u.deletedFlag=0")
    List<UserInfoEntity> getUserInfoListByScoreRange(int min, int max);

    Optional<UserInfoEntity> getUserInfoEntityByUserIdAndDeletedFlag(Long userId, Integer deleted);
}
