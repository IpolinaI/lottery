package com.demo.lottery.domain.service;

import com.demo.lottery.domain.exception.LotteryFinishedException;
import com.demo.lottery.domain.exception.LotteryNotFoundException;
import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.BallotRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.LotteryRepositoryPort;
import com.demo.lottery.domain.utils.UserUtils;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BallotService.class)
public class BallotServiceTest {

    @MockBean
    private UserUtils userUtils;

    @MockBean
    private BallotRepositoryPort ballotRepositoryPort;

    @MockBean
    private LotteryRepositoryPort lotteryRepositoryPort;

    @Autowired
    private BallotService ballotService;

    @Test
    public void testCreateThrowsLotteryNotFoundException() {
        var ballot = Ballot.builder().lotteryUuid(UUID.randomUUID()).userUuid(UUID.randomUUID()).build();

        when(lotteryRepositoryPort.findByUuid(ballot.getLotteryUuid())).thenReturn(Optional.empty());

        assertThrows(LotteryNotFoundException.class, () -> ballotService.create(ballot));
    }

    @Test
    public void testCreateThrowsLotteryFinishedException() {
        var ballot = Ballot.builder().lotteryUuid(UUID.randomUUID()).userUuid(UUID.randomUUID()).build();
        var lottery = Lottery.builder()
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now().minusDays(6))
                .build();
        var user = User.builder().username("test_username").password("test_password").build();

        when(lotteryRepositoryPort.findByUuid(ballot.getLotteryUuid())).thenReturn(Optional.of(lottery));
        when(userUtils.getUserFromContext()).thenReturn(user);

        assertThrows(LotteryFinishedException.class, () -> ballotService.create(ballot));
    }

    @Test
    public void testCreateReturnsExpectedResult() {
        var lottery = Lottery.builder()
                .uuid(UUID.randomUUID())
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now())
                .build();
        var ballot = Ballot.builder().lotteryUuid(lottery.getUuid()).userUuid(UUID.randomUUID()).build();
        var user = User.builder().username("test_username").password("test_password").build();

        var expectedBallot = ballot.toBuilder().uuid(UUID.randomUUID()).build();
        when(lotteryRepositoryPort.findByUuid(ballot.getLotteryUuid())).thenReturn(Optional.of(lottery));
        when(userUtils.getUserFromContext()).thenReturn(user);
        when(ballotRepositoryPort.save(ballot)).thenReturn(expectedBallot);

        var actualResult = ballotService.create(ballot);

        assertThat(actualResult).isEqualTo(expectedBallot);
    }

    @Test
    public void testGetAllForUserReturnsExpectedResult() {
        var user = User.builder().uuid(UUID.randomUUID())
                .username("test_username")
                .password("test_password").build();
        var pageable = Pageable.unpaged();
        var expectedBallots = new PageImpl<>(List.of(Ballot.builder()
                .userUuid(user.getUuid())
                .lotteryUuid(UUID.randomUUID()).build()));

        when(userUtils.getUserFromContext()).thenReturn(user);
        when(ballotRepositoryPort.findAllByUserUuid(user.getUuid(), pageable)).thenReturn(expectedBallots);

        var actualResult = ballotService.getAllForUser(pageable);

        assertThat(actualResult).isEqualTo(expectedBallots);
    }
}
