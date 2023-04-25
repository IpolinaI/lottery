package com.demo.lottery.infrastructure.in.web.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class LotteryResponse {
    private UUID uuid;

    private int durationDays;

    private int winnersAmount;

    private LocalDate createdAt;
}
