package com.demo.lottery.infrastructure.out.persistence.repository.adapter;

import com.demo.lottery.domain.model.User;
import com.demo.lottery.domain.port.out.persistence.UserRepositoryPort;
import com.demo.lottery.infrastructure.out.persistence.mapper.UserMapper;
import com.demo.lottery.infrastructure.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::map);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(userMapper.map(user));
    }
}
