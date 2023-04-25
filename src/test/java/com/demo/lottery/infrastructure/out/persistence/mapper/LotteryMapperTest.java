package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
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
public class LotteryMapperTest {

    @Autowired
    private LotteryMapper lotteryMapper;

    @Test
    public void testMapReturnsLottery() {
        var lotteryEntity = LotteryEntity.builder()
                .uuid(UUID.randomUUID())
                .durationDays(1)
                .winnersAmount(1)
                .createdAt(LocalDate.now())
                .build();

        var actualResult = lotteryMapper.map(lotteryEntity);

        assertThat(actualResult.getUuid()).isEqualTo(lotteryEntity.getUuid());
        assertThat(actualResult.getDurationDays()).isEqualTo(lotteryEntity.getDurationDays());
        assertThat(actualResult.getWinnersAmount()).isEqualTo(lotteryEntity.getWinnersAmount());
        assertThat(actualResult.getCreatedAt()).isEqualTo(lotteryEntity.getCreatedAt());
    }

    @Test
    public void testMapReturnsLotteryEntity() {
        var lottery = Lottery.builder()
                .uuid(UUID.randomUUID())
                .durationDays(5)
                .winnersAmount(5)
                .createdAt(LocalDate.now())
                .build();

        var actualResult = lotteryMapper.map(lottery);

        assertThat(actualResult.getUuid()).isEqualTo(lottery.getUuid());
        assertThat(actualResult.getDurationDays()).isEqualTo(lottery.getDurationDays());
        assertThat(actualResult.getWinnersAmount()).isEqualTo(lottery.getWinnersAmount());
        assertThat(actualResult.getCreatedAt()).isEqualTo(lottery.getCreatedAt());
    }
}
