package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.infrastructure.out.persistence.entity.BallotEntity;
import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
import com.demo.lottery.infrastructure.out.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public class BallotMapperTest {

    @Autowired
    private BallotMapper ballotMapper;

    @Test
    public void testMapReturnsBallot() {
        var ballotEntity = BallotEntity.builder()
                .uuid(UUID.randomUUID())
                .user(UserEntity.builder().uuid(UUID.randomUUID()).build())
                .isWinning(true)
                .lottery(LotteryEntity.builder().uuid(UUID.randomUUID()).build())
                .build();

        var actualResult = ballotMapper.map(ballotEntity);

        assertThat(actualResult.getUuid()).isEqualTo(ballotEntity.getUuid());
        assertThat(actualResult.getUserUuid()).isEqualTo(ballotEntity.getUser().getUuid());
        assertThat(actualResult.getLotteryUuid()).isEqualTo(ballotEntity.getLottery().getUuid());
        assertThat(actualResult.getIsWinning()).isEqualTo(ballotEntity.getIsWinning());
    }

    @Test
    public void testMapReturnsBallotEntity() {
        var ballot = Ballot.builder()
                .uuid(UUID.randomUUID())
                .lotteryUuid(UUID.randomUUID())
                .userUuid(UUID.randomUUID())
                .isWinning(true)
                .build();

        var actualResult = ballotMapper.map(ballot);

        assertThat(actualResult.getUuid()).isEqualTo(ballot.getUuid());
        assertThat(actualResult.getLottery().getUuid()).isEqualTo(ballot.getLotteryUuid());
        assertThat(actualResult.getUser().getUuid()).isEqualTo(ballot.getUserUuid());
        assertThat(actualResult.getIsWinning()).isEqualTo(ballot.getIsWinning());
    }
}
