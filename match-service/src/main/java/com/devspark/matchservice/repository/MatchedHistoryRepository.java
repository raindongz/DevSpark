package com.devspark.matchservice.repository;

import com.devspark.matchservice.entity.MatchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchedHistoryRepository extends JpaRepository<MatchHistoryEntity, Long> {

    List<MatchHistoryEntity> findAllByMatchIdOne(Long userId);
    List<MatchHistoryEntity> findAllByMatchIdTwo(Long userId);

    Optional<MatchHistoryEntity> findByMatchIdOneAndMatchIdTwo(Long idOne, Long idTwo);

    void deleteByMatchIdOneAndMatchIdTwo(Long idOne, Long idTwo);
}
