package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.BaseIntegrationTest;
import com.demo.lottery.infrastructure.in.web.dto.request.LotteryCreateRequest;
import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
import com.demo.lottery.infrastructure.out.persistence.repository.LotteryRepository;
import com.demo.lottery.infrastructure.out.persistence.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/data.sql")
public class LotteryControllerTest extends BaseIntegrationTest {

    private static final String BASE_LOTTERIES_URL = "/api/lotteries";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        lotteryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateReturnsCreatedStatus() throws Exception {
        var lotteryCreateRequest = LotteryCreateRequest.builder()
                .durationDays(5)
                .winnersAmount(5)
                .build();

        mockMvc.perform(post(BASE_LOTTERIES_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(lotteryCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("durationDays").value(lotteryCreateRequest.getDurationDays()))
                .andExpect(jsonPath("winnersAmount").value(lotteryCreateRequest.getWinnersAmount()));
    }

    @Test
    public void testCreateReturnsUnauthorizedStatus() throws Exception {
        var lotteryCreateRequest = LotteryCreateRequest.builder()
                .durationDays(5)
                .winnersAmount(5)
                .build();

        mockMvc.perform(post(BASE_LOTTERIES_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(lotteryCreateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateReturnsBadRequestStatus() throws Exception {
        var lotteryCreateRequest = LotteryCreateRequest.builder()
                .durationDays(0)
                .winnersAmount(5)
                .build();

        mockMvc.perform(post(BASE_LOTTERIES_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(lotteryCreateRequest)))
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

        mockMvc.perform(get(BASE_LOTTERIES_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "A2evd5V")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].uuid").value(lottery.getUuid().toString()))
                .andExpect(jsonPath("[0].winnersAmount").value(lottery.getWinnersAmount()))
                .andExpect(jsonPath("[0].durationDays").value(lottery.getDurationDays()))
                .andExpect(jsonPath("[0].createdAt").value(lottery.getCreatedAt().toString()));
    }

    @Test
    public void testGetAllUnauthorizedStatus() throws Exception {
        mockMvc.perform(get(BASE_LOTTERIES_URL)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
                .andExpect(status().isUnauthorized());
    }
}
