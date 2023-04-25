package com.demo.lottery.domain.service;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LotteryUserDetailService.class)
public class LotteryUserDetailServiceTest {

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    @Autowired
    private LotteryUserDetailService lotteryUserDetailService;

    @Test
    public void testLoadUserByUsernameThrowsUsernameNotFoundException() {
        var username = "test_username";

        when(userRepositoryPort.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> lotteryUserDetailService.loadUserByUsername(username));
    }

    @Test
    public void testLoadUserByUsernameReturnsExpectedResult() {
        var user = User.builder()
                .username("test_username")
                .password("test_password")
                .roles(Set.of(Role.builder().id(1).name(RoleName.ROLE_PLAYER).build()))
                .build();
        var grantedAuthorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString())).collect(Collectors.toSet());
        var expectedUserDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);

        when(userRepositoryPort.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        var actualResult = lotteryUserDetailService.loadUserByUsername(user.getUsername());

        assertThat(actualResult).isEqualTo(expectedUserDetails);
    }
}
