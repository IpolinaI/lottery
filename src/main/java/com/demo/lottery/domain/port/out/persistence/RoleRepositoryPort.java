package com.demo.lottery.domain.port.out.persistence;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(RoleName name);
}
