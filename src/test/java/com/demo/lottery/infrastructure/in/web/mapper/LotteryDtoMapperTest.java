package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.infrastructure.in.web.dto.request.LotteryCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public class LotteryDtoMapperTest {

    @Autowired
    private LotteryDtoMapper lotteryDtoMapper;

    @Test
    public void testMapReturnsLottery() {
        var lotteryCreateRequest = LotteryCreateRequest.builder()
                .durationDays(5)
                .winnersAmount(5)
                .build();

        var actualResult = lotteryDtoMapper.map(lotteryCreateRequest);

        assertThat(actualResult.getDurationDays()).isEqualTo(lotteryCreateRequest.getDurationDays());
        assertThat(actualResult.getWinnersAmount()).isEqualTo(lotteryCreateRequest.getWinnersAmount());
    }

    @Test
    public void testMapReturnsLotteryResponse() {
        var lottery = Lottery.builder()
                .uuid(UUID.randomUUID())
                .durationDays(5)
                .winnersAmount(5)
                .createdAt(LocalDate.now())
                .build();

        var actualResult = lotteryDtoMapper.map(lottery);

        assertThat(actualResult.getDurationDays()).isEqualTo(lottery.getDurationDays());
        assertThat(actualResult.getWinnersAmount()).isEqualTo(lottery.getWinnersAmount());
        assertThat(actualResult.getCreatedAt()).isEqualTo(lottery.getCreatedAt());
        assertThat(actualResult.getUuid()).isEqualTo(lottery.getUuid());
    }
}
