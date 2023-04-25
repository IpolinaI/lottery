package com.demo.lottery.infrastructure.out.persistence.mapper;

import com.demo.lottery.domain.model.Lottery;
import com.demo.lottery.infrastructure.out.persistence.entity.LotteryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LotteryMapper {
    Lottery map(LotteryEntity lotteryEntity);

    @Mapping(target = "ballots", ignore = true)
    LotteryEntity map(Lottery lottery);

    List<Lottery> map(List<LotteryEntity> lotteryEntityList);
}
