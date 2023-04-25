package com.demo.lottery.domain.service;

import com.demo.lottery.domain.exception.UsernameTakenException;
import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.RoleRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthenticationService.class)
public class AuthenticationServiceTest {

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    @MockBean
    private RoleRepositoryPort roleRepositoryPort;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_PASSWORD = "test_password";
    private static final String TEST_ENCRYPTED_PASSWORD = "test_encrypted_password";

    @Test
    public void testRegisterUserThrowsUsernameTakenException(){
        var user = User.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();

        when(userRepositoryPort.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(UsernameTakenException.class, () -> authenticationService.registerUser(user));
    }

    @Test
    public void testRegisterUserDoesNotThrowException(){
        var user = User.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();
        var role = Role.builder().id(1).name(RoleName.ROLE_PLAYER).build();

        when(userRepositoryPort.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(TEST_ENCRYPTED_PASSWORD);
        when(roleRepositoryPort.findByName(RoleName.ROLE_PLAYER)).thenReturn(Optional.of(role));

        var expectedUser = user.toBuilder().roles(Set.of(role)).password(TEST_ENCRYPTED_PASSWORD).build();

        authenticationService.registerUser(user);

        verify(userRepositoryPort).save(expectedUser);
    }
}
