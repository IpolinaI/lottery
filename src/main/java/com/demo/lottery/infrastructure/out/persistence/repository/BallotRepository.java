package com.demo.lottery.infrastructure.out.persistence.repository;

import com.demo.lottery.infrastructure.out.persistence.entity.BallotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BallotRepository extends JpaRepository<BallotEntity, UUID> {
    Page<BallotEntity> findAllByUserUuid(UUID userUuid, Pageable pageable);
    List<BallotEntity> findAllByLotteryUuid(UUID lotteryUuid);
}
