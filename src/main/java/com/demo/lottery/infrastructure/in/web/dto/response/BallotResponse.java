package com.demo.lottery.infrastructure.in.web.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BallotResponse {
    private UUID uuid;

    private UUID userUuid;

    private UUID lotteryUuid;

    private Boolean isWinning;
}
