package com.demo.lottery.infrastructure.in.web.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LotteryCreateRequest {
    @Min(value = 1, message = "must be greater than 0")
    @Max(value = 100, message = "must be less or equal to 100")
    private int durationDays;

    @Min(value = 1, message = "must be greater than 0")
    @Max(value = 100, message = "must be less or equal to 100")
    private int winnersAmount;
}
