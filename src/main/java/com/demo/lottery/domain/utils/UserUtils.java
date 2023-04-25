package com.demo.lottery.domain.utils;

import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepositoryPort userRepositoryPort;

    public User getUserFromContext() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var username = ((UserDetails) principal).getUsername();
        return userRepositoryPort.findByUsername(username).orElseThrow();
    }
}
