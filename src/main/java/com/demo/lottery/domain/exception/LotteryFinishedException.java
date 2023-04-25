package com.demo.lottery.domain.exception;

import java.util.UUID;

public class LotteryFinishedException extends RuntimeException {
    public LotteryFinishedException(UUID lotteryUuid) {
        super(String.format("Lottery with uuid %s is already finished", lotteryUuid.toString()));
    }
}
