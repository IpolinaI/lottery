package com.demo.lottery.domain.service;

import com.demo.lottery.domain.exception.UsernameTakenException;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.RoleRepositoryPort;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepositoryPort userRepositoryPort;

    private final RoleRepositoryPort roleRepositoryPort;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(User user){
        if (userRepositoryPort.existsByUsername(user.getUsername())){
            throw new UsernameTakenException(user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var role = roleRepositoryPort.findByName(RoleName.ROLE_PLAYER).orElseThrow();
        user.setRoles(Collections.singleton(role));

        userRepositoryPort.save(user);
    }
}
