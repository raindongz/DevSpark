package com.devspark.matchservice.repository;

import com.devspark.matchservice.entity.LikeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeHistoryRepository extends JpaRepository<LikeHistoryEntity, Long> {

    List<LikeHistoryEntity> findAllByPersonBeLiked(Long userId);
    List<LikeHistoryEntity> findAllByPersonClickLiked(Long userId);

    void deleteByPersonClickLikedAndPersonBeLiked(Long clickId, Long beLiked);
}
