package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.infrastructure.in.web.dto.request.UserLoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class UserDtoMapperTest {

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Test
    public void testMapReturnsUser() {
        var userLoginRequest = UserLoginRequest.builder()
                .username("test_username")
                .password("test_password")
                .build();

        var actualResult = userDtoMapper.map(userLoginRequest);

        assertThat(actualResult.getUsername()).isEqualTo(userLoginRequest.getUsername());
        assertThat(actualResult.getPassword()).isEqualTo(userLoginRequest.getPassword());
    }
}
