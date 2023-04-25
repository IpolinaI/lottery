package com.demo.lottery.domain.utils;

import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserUtils.class)
public class UserUtilsTest {

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    @Autowired
    private UserUtils userUtils;

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_PASSWORD = "test_password";

    @Test
    @WithMockUser(username = TEST_USERNAME, authorities = { "ROLE_PLAYER" })
    public void getUserFromContext() {
        var user = User.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();

        when(userRepositoryPort.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

        var actualResult = userUtils.getUserFromContext();

        assertThat(actualResult).isEqualTo(user);
    }
}
