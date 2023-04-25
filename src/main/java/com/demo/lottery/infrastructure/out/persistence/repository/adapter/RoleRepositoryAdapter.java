package com.demo.lottery.infrastructure.out.persistence.repository.adapter;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.domain.port.out.persistence.RoleRepositoryPort;
import com.demo.lottery.infrastructure.out.persistence.mapper.RoleMapper;
import com.demo.lottery.infrastructure.out.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name).map(roleMapper::map);
    }
}
