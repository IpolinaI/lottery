package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Role;
import com.demo.lottery.domain.model.RoleName;
import com.demo.lottery.domain.model.User;
import com.demo.lottery.infrastructure.out.persistence.entity.RoleEntity;
import com.demo.lottery.infrastructure.out.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMapReturnsUser() {
        var userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .username("test_username")
                .password("test_password")
                .roles(Set.of(RoleEntity.builder().id(1).name(RoleName.ROLE_PLAYER).build()))
                .build();

        var actualResult = userMapper.map(userEntity);

        assertThat(actualResult.getUuid()).isEqualTo(userEntity.getUuid());
        assertThat(actualResult.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(actualResult.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(actualResult.getRoles().size()).isEqualTo(userEntity.getRoles().size());
    }

    @Test
    public void testMapReturnsUserEntity() {
        var user = User.builder()
                .uuid(UUID.randomUUID())
                .username("test_username")
                .password("test_password")
                .roles(Set.of(Role.builder().id(1).name(RoleName.ROLE_PLAYER).build()))
                .build();

        var actualResult = userMapper.map(user);

        assertThat(actualResult.getUuid()).isEqualTo(user.getUuid());
        assertThat(actualResult.getPassword()).isEqualTo(user.getPassword());
        assertThat(actualResult.getUsername()).isEqualTo(user.getUsername());
        assertThat(actualResult.getRoles().size()).isEqualTo(user.getRoles().size());
    }
}
