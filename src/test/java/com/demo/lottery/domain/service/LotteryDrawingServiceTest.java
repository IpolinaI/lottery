package com.demo.lottery.domain.service;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.port.out.persistence.BallotRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LotteryDrawingService.class)
public class LotteryDrawingServiceTest {

    @MockBean
    private LotteryRepositoryPort lotteryRepositoryPort;

    @MockBean
    private BallotRepositoryPort ballotRepositoryPort;

    @Autowired
    private LotteryDrawingService lotteryDrawingService;

    @Test
    public void testDrawLotteries() {
        var lottery = Lottery
                .builder()
                .uuid(UUID.randomUUID())
                .winnersAmount(1)
                .durationDays(5)
                .createdAt(LocalDate.now().minusDays(5)).build();
        var lotteries = new PageImpl<>(List.of(lottery));
        var ballots = List.of(getBallot(lottery.getUuid()), getBallot(lottery.getUuid()));

        when(lotteryRepositoryPort.findAllByClosingDate(LocalDate.now(), PageRequest.of(0, 10))).thenReturn(lotteries);
        when(ballotRepositoryPort.findAllByLotteryUuid(lottery.getUuid())).thenReturn(ballots);

        lotteryDrawingService.drawLotteries();

        ArgumentCaptor<List> ballotsListCaptor = ArgumentCaptor.forClass(List.class);
        verify(ballotRepositoryPort).saveAll(ballotsListCaptor.capture());

        assertThat(ballotsListCaptor.getValue().size()).isEqualTo(ballots.size());
        assertThat(ballotsListCaptor.getValue().stream()
                .filter(ballot -> ((Ballot) ballot).getIsWinning() == false).count()).isEqualTo(1);
        assertThat(ballotsListCaptor.getValue().stream()
                .filter(ballot -> ((Ballot) ballot).getIsWinning()).count()).isEqualTo(1);
    }

    private static Ballot getBallot(UUID lotteryUuid) {
        return Ballot.builder()
                .uuid(UUID.randomUUID())
                .lotteryUuid(lotteryUuid)
                .userUuid(UUID.randomUUID())
                .build();
    }
}
