package com.demo.lottery.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Ballot {
    private UUID uuid;

    private UUID userUuid;

    private UUID lotteryUuid;

    private Boolean isWinning;
}
