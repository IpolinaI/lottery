package com.demo.lottery.domain.port.out.persistence;

import com.demo.lottery.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void save(User user);
}
