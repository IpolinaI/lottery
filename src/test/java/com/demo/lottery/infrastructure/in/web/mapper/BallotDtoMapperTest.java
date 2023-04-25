package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.infrastructure.in.web.dto.request.BallotCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public class BallotDtoMapperTest {

    @Autowired
    private BallotDtoMapper ballotDtoMapper;

    @Test
    public void testMapReturnsBallot() {
        var ballotCreateRequest = BallotCreateRequest.builder()
                .lotteryUuid(UUID.randomUUID())
                .build();

        var actualResult = ballotDtoMapper.map(ballotCreateRequest);

        assertThat(actualResult.getLotteryUuid()).isEqualTo(ballotCreateRequest.getLotteryUuid());
    }

    @Test
    public void testMapReturnsBallotResponse() {
        var ballot = Ballot.builder()
                .uuid(UUID.randomUUID())
                .lotteryUuid(UUID.randomUUID())
                .userUuid(UUID.randomUUID())
                .isWinning(true)
                .build();

        var actualResult = ballotDtoMapper.map(ballot);

        assertThat(actualResult.getLotteryUuid()).isEqualTo(ballot.getLotteryUuid());
        assertThat(actualResult.getIsWinning()).isEqualTo(ballot.getIsWinning());
        assertThat(actualResult.getUuid()).isEqualTo(ballot.getUuid());
        assertThat(actualResult.getUserUuid()).isEqualTo(ballot.getUserUuid());
    }
}
