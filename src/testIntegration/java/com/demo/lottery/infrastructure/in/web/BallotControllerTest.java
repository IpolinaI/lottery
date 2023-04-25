package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.BaseIntegrationTest;
import com.demo.lottery.infrastructure.in.web.dto.request.BallotCreateRequest;
import com.demo.lottery.infrastructure.out.persistence.entity.BallotEntity;
import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
import com.demo.lottery.infrastructure.out.persistence.entity.UserEntity;
import com.demo.lottery.infrastructure.out.persistence.repository.BallotRepository;
import com.demo.lottery.infrastructure.out.persistence.repository.LotteryRepository;
import com.demo.lottery.infrastructure.out.persistence.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/data.sql")
public class BallotControllerTest extends BaseIntegrationTest {

    private static final String BASE_BALLOTS_URL = "/api/ballots";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BallotRepository ballotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @AfterEach
    public void cleanUp() {
        ballotRepository.deleteAll();
        userRepository.deleteAll();
        lotteryRepository.deleteAll();
    }

    @Test
    public void testCreateReturnsCreatedStatus() throws Exception {
        var lottery = LotteryEntity.builder()
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now())
                .build();

        lottery = lotteryRepository.save(lottery);

        var ballotCreateRequest =  BallotCreateRequest.builder()
                .lotteryUuid(lottery.getUuid())
                .build();

        mockMvc.perform(post(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(ballotCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("lotteryUuid").value(ballotCreateRequest.getLotteryUuid().toString()));
    }

    @Test
    public void testCreateReturnsNotFoundStatus() throws Exception {
        var ballotCreateRequest =  BallotCreateRequest.builder()
                .lotteryUuid(UUID.randomUUID())
                .build();

        mockMvc.perform(post(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(ballotCreateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateReturnsUnauthorizedStatus() throws Exception {
        var ballotCreateRequest =  BallotCreateRequest.builder()
                .lotteryUuid(UUID.randomUUID())
                .build();

        mockMvc.perform(post(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(ballotCreateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateReturnsBadRequestStatus() throws Exception {
        var ballotCreateRequest = BallotCreateRequest.builder()
                .lotteryUuid(null)
                .build();

        mockMvc.perform(post(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(ballotCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllReturnsOkStatus() throws Exception {
        var lottery = LotteryEntity.builder()
                .winnersAmount(5)
                .durationDays(5)
                .createdAt(LocalDate.now())
                .build();

        lottery = lotteryRepository.save(lottery);

        var ballot = BallotEntity.builder()
                .user(UserEntity.builder().uuid(UUID.fromString("a4964fc2-7adc-4fd8-b055-abd3a75acc54")).build())
                .isWinning(true)
                .lottery(lottery)
                .build();

        ballot = ballotRepository.save(ballot);

        mockMvc.perform(get(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].uuid").value(ballot.getUuid().toString()))
                .andExpect(jsonPath("[0].userUuid").value(ballot.getUser().getUuid().toString()))
                .andExpect(jsonPath("[0].lotteryUuid").value(ballot.getLottery().getUuid().toString()))
                .andExpect(jsonPath("[0].isWinning").value(ballot.getIsWinning()));
    }

    @Test
    public void testGetAllUnauthorizedStatus() throws Exception {
        mockMvc.perform(get(BASE_BALLOTS_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
                .andExpect(status().isUnauthorized());
    }
}
