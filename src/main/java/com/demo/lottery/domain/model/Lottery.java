package com.demo.lottery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Lottery {
    private UUID uuid;

    private int durationDays;

    private int winnersAmount;

    private LocalDate createdAt;
}
