package com.demo.lottery.domain.port.out.persistence;

import com.demo.lottery.domain.model.Ballot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BallotRepositoryPort {
    Ballot save(Ballot ballot);

    void saveAll(List<Ballot> ballots);

    Page<Ballot> findAllByUserUuid(UUID userUuid, Pageable pageable);

    List<Ballot> findAllByLotteryUuid(UUID lotteryUuid);
}
