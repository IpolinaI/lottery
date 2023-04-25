package com.demo.lottery.infrastructure.out.persistence.repository.adapter;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import com.demo.lottery.infrastructure.out.persistence.mapper.LotteryMapper;
import com.demo.lottery.infrastructure.out.persistence.repository.LotteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class LotteryRepositoryAdapter implements LotteryRepositoryPort {

    private final LotteryRepository lotteryRepository;
    private final LotteryMapper lotteryMapper;

    @Override
    public Lottery save(Lottery lottery) {
        var savedLottery = lotteryRepository.save(lotteryMapper.map(lottery));

        return lotteryMapper.map(savedLottery);
    }

    @Override
    public Optional<Lottery> findByUuid(UUID uuid) {
        return lotteryRepository.findById(uuid).map(lotteryMapper::map);
    }

    @Override
    public Page<Lottery> findAll(Pageable pageable) {
        return lotteryRepository.findAll(pageable).map(lotteryMapper::map);
    }

    @Override
    public Page<Lottery> findAllByClosingDate(LocalDate date, Pageable pageable) {
        return lotteryRepository.findAllByClosingDate(date, pageable).map(lotteryMapper::map);
    }
}
