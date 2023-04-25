package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.infrastructure.out.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    Role map(RoleEntity roleEntity);

    RoleEntity map(Role role);
}
