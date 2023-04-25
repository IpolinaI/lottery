package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.BaseIntegrationTest;
import com.demo.lottery.infrastructure.in.web.dto.request.UserLoginRequest;
import com.demo.lottery.infrastructure.out.persistence.entity.UserEntity;
import com.demo.lottery.infrastructure.out.persistence.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends BaseIntegrationTest {

    private static final String REGISTER_USER_URL = "/api/auth/signup";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUserReturnsCreatedStatus() throws Exception {
        var userLoginRequest = UserLoginRequest.builder()
                .username("test_username")
                .password("test_password")
                .build();

        mockMvc.perform(post(REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userLoginRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterUserReturnsUnprocessableEntityStatus() throws Exception {
        var userLoginRequest = UserLoginRequest.builder()
                .username("test_username")
                .password("test_password")
                .build();

        userRepository.save(UserEntity.builder().username("test_username").password("").build());

        mockMvc.perform(post(REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userLoginRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testRegisterUserReturnsBadRequestStatus() throws Exception {
        var userLoginRequest = UserLoginRequest.builder()
                .username("test_username")
                .password("p")
                .build();

        mockMvc.perform(post(REGISTER_USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userLoginRequest)))
                .andExpect(status().isBadRequest());
    }
}
