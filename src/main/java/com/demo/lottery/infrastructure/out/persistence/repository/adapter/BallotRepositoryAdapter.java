package com.demo.lottery.infrastructure.out.persistence.repository.adapter;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.domain.port.out.persistence.BallotRepositoryPort;
import com.demo.lottery.infrastructure.out.persistence.mapper.BallotMapper;
import com.demo.lottery.infrastructure.out.persistence.repository.BallotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BallotRepositoryAdapter implements BallotRepositoryPort {

    private final BallotRepository ballotRepository;
    private final BallotMapper ballotMapper;

    @Override
    public Ballot save(Ballot ballot) {
        var savedBallot = ballotRepository.save(ballotMapper.map(ballot));
        return ballotMapper.map(savedBallot);
    }

    @Override
    public void saveAll(List<Ballot> ballots) {
        ballotRepository.saveAll(ballotMapper.mapDomainsList(ballots));
    }

    @Override
    public Page<Ballot> findAllByUserUuid(UUID userUuid, Pageable pageable) {
        return ballotRepository.findAllByUserUuid(userUuid, pageable).map(ballotMapper::map);
    }

    @Override
    public List<Ballot> findAllByLotteryUuid(UUID lotteryUuid) {
        return ballotMapper.mapEntitiesList(ballotRepository.findAllByLotteryUuid(lotteryUuid));
    }
}
