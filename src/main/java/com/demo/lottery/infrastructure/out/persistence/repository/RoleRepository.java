package com.demo.lottery.infrastructure.out.persistence.repository;

import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.infrastructure.out.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}
