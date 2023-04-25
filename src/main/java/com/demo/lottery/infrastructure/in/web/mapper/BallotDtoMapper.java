package com.demo.lottery.infrastructure.in.web.mapper;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.infrastructure.in.web.dto.request.BallotCreateRequest;
import com.demo.lottery.infrastructure.in.web.dto.response.BallotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BallotDtoMapper {

    @Mappings({
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "userUuid", ignore = true),
            @Mapping(target = "isWinning", ignore = true)
    })
    Ballot map(BallotCreateRequest ballotCreateRequest);

    BallotResponse map(Ballot lottery);
}
