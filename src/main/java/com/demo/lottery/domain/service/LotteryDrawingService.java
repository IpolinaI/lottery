package com.demo.lottery.domain.service;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.port.out.persistence.BallotRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LotteryDrawingService {

    private static int BATCH_SIZE = 10;

    private final LotteryRepositoryPort lotteryRepositoryPort;
    private final BallotRepositoryPort ballotRepositoryPort;

    @Scheduled(cron = "@daily")
    public void drawLotteries() {
        log.info("[LOTTERY DRAWING] Daily lotteries drawing has started");

        try {
            var page = lotteryRepositoryPort.findAllByClosingDate(LocalDate.now(), PageRequest.of(0, BATCH_SIZE));
            var lotteriesInBatch = page.getContent();
            lotteriesInBatch.forEach(this::defineWinningBallots);

            while (page.hasNext()) {
                page = lotteryRepositoryPort.findAllByClosingDate(LocalDate.now(), page.nextPageable());
                page.get().forEach(this::defineWinningBallots);
            }
        } catch (Exception ex) {
            log.error("[LOTTERY DRAWING] Daily lotteries drawing has unexpectedly failed with an error: {}", ex.getMessage());
            return;
        }

        log.info("[LOTTERY DRAWING] Daily lotteries drawing has finished");
    }

    private void defineWinningBallots(Lottery lottery) {
        var ballots = ballotRepositoryPort.findAllByLotteryUuid(lottery.getUuid());

        if (ballots.size() < lottery.getWinnersAmount() * 2) {
            log.warn("[LOTTERY DRAWING] Participants amount has to be at least twice bigger than winners amount, drawing failed for lottery: "
                    + lottery.getUuid());
            return;
        }
        var winningNumbers = new Random().ints(lottery.getWinnersAmount(), 1, ballots.size());

        ballots.forEach(ballot -> ballot.setIsWinning(false));

        winningNumbers.forEach(winningNumber -> ballots.get(winningNumber).setIsWinning(true));

        ballotRepositoryPort.saveAll(ballots);
    }
}
