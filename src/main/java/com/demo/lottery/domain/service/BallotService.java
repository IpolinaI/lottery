package com.demo.lottery.domain.service;

import com.demo.lottery.domain.exception.LotteryFinishedException;
import com.demo.lottery.domain.exception.LotteryNotFoundException;
import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.domain.port.out.persistence.BallotRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import com.demo.lottery.domain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BallotService {

    private final UserUtils userUtils;
    private final BallotRepositoryPort ballotRepositoryPort;
    private final LotteryRepositoryPort lotteryRepositoryPort;

    public Ballot create(Ballot ballot) {
        var lottery = lotteryRepositoryPort.findByUuid(ballot.getLotteryUuid())
                .orElseThrow(() -> new LotteryNotFoundException(ballot.getLotteryUuid()));

        if (lottery.getCreatedAt().plusDays(lottery.getDurationDays()).isBefore(LocalDate.now())) {
            throw new LotteryFinishedException(ballot.getLotteryUuid());
        }

        var user = userUtils.getUserFromContext();
        ballot.setUserUuid(user.getUuid());

        return ballotRepositoryPort.save(ballot);
    }

    public Page<Ballot> getAllForUser(Pageable pageable) {
        var user = userUtils.getUserFromContext();
        return ballotRepositoryPort.findAllByUserUuid(user.getUuid(), pageable);
    }
}
