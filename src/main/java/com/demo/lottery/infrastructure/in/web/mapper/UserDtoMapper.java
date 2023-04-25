package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.domain.model.User;
import com.demo.lottery.infrastructure.in.web.dto.request.UserLoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {
    @Mappings({
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    User map(UserLoginRequest userLoginRequest);
}
