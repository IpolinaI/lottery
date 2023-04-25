package com.demo.lottery.infrastructure.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BallotCreateRequest {
    @NotNull(message = "must not be null")
    private UUID lotteryUuid;
}
