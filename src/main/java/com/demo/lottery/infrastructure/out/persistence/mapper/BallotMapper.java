package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Ballot;
import com.demo.lottery.infrastructure.out.persistence.entity.BallotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BallotMapper {

    @Mappings({
            @Mapping(source = "user.uuid", target = "userUuid"),
            @Mapping(source = "lottery.uuid", target = "lotteryUuid")
    })
    Ballot map(BallotEntity ballotEntity);

    @Mappings({
            @Mapping(source = "userUuid", target = "user.uuid"),
            @Mapping(source = "lotteryUuid", target = "lottery.uuid")
    })
    BallotEntity map(Ballot ballot);

    List<BallotEntity> mapDomainsList(List<Ballot> ballots);

    List<Ballot> mapEntitiesList(List<BallotEntity> ballotEntities);
}
