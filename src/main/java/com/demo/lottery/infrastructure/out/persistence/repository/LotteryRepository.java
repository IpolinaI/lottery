package com.demo.lottery.infrastructure.out.persistence.repository;

import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface LotteryRepository extends JpaRepository<LotteryEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM lottery l WHERE l.created_at + INTERVAL '1' day * l.duration_days = :date")
    Page<LotteryEntity> findAllByClosingDate(LocalDate date, Pageable pageable);
}
