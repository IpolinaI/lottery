package com.demo.lottery.domain.exception;

import java.util.UUID;

public class LotteryNotFoundException extends RuntimeException {
    public LotteryNotFoundException(UUID lotteryUuid) {
        super(String.format("Lottery with uuid %s is not found", lotteryUuid.toString()));
    }
}
