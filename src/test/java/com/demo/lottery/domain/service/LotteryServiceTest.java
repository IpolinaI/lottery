package com.demo.lottery.domain.service;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LotteryService.class)
public class LotteryServiceTest {

    @MockBean
    private LotteryRepositoryPort lotteryRepositoryPort;

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void testCreateReturnsExpectedResult() {
        var lottery = Lottery.builder()
                .uuid(UUID.randomUUID())
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now())
                .build();

        when(lotteryRepositoryPort.save(lottery)).thenReturn(lottery);

        var actualResult = lotteryService.create(lottery);

        assertThat(actualResult).isEqualTo(lottery);
    }

    @Test
    public void testGetAllReturnsExpectedResult() {
        var lottery = Lottery.builder()
                .uuid(UUID.randomUUID())
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now())
                .build();
        var pageable = Pageable.unpaged();
        var expectedLotteries = new PageImpl<>(List.of(lottery));

        when(lotteryRepositoryPort.findAll(pageable)).thenReturn(expectedLotteries);

        var actualResult = lotteryService.getAll(pageable);

        assertThat(actualResult).isEqualTo(expectedLotteries);
    }
}
