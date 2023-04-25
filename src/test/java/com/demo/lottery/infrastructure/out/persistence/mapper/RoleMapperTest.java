package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.infrastructure.out.persistence.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testMapReturnsRole() {
        var roleEntity = RoleEntity.builder()
                .id(1)
                .name(RoleName.ROLE_PLAYER)
                .build();

        var actualResult = roleMapper.map(roleEntity);

        assertThat(actualResult.getId()).isEqualTo(roleEntity.getId());
        assertThat(actualResult.getName()).isEqualTo(roleEntity.getName());
    }

    @Test
    public void testMapReturnsRoleEntity() {
        var role = Role.builder()
                .id(1)
                .name(RoleName.ROLE_PLAYER)
                .build();

        var actualResult = roleMapper.map(role);

        assertThat(actualResult.getId()).isEqualTo(role.getId());
        assertThat(actualResult.getName()).isEqualTo(role.getName());
    }
}
