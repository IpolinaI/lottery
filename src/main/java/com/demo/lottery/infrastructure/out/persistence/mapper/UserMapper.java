package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.User;
import com.demo.lottery.infrastructure.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RoleMapper.class)
public interface UserMapper {
    User map(UserEntity userEntity);

    @Mapping(target = "ballots", ignore = true)
    UserEntity map(User user);
}
