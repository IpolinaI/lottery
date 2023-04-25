package com.demo.lottery.infrastructure.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String statusCode;
    private String message;
}
