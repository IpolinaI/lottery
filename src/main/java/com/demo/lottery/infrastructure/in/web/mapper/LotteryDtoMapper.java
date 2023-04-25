package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.infrastructure.in.web.dto.request.LotteryCreateRequest;
import com.demo.lottery.infrastructure.in.web.dto.response.LotteryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LotteryDtoMapper {

    @Mappings({
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    Lottery map(LotteryCreateRequest lotteryCreateRequest);

    LotteryResponse map(Lottery lottery);
}
