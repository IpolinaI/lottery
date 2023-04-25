package com.demo.lottery.domain.service;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotteryService {

    private final LotteryRepositoryPort lotteryRepositoryPort;

    public Lottery create(Lottery lottery) {
        return lotteryRepositoryPort.save(lottery);
    }

    public Page<Lottery> getAll(Pageable pageable) {
        return lotteryRepositoryPort.findAll(pageable);
    }
}
