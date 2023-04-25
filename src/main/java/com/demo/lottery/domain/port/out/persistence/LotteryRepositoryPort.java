package com.demo.lottery.domain.port.out.persistence;

import com.demo.lottery.domain.model.Lottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


public interface LotteryRepositoryPort {
    Lottery save(Lottery lottery);

    Optional<Lottery> findByUuid(UUID uuid);

    Page<Lottery> findAll(Pageable pageable);

    Page<Lottery> findAllByClosingDate(LocalDate date, Pageable pageable);
}
