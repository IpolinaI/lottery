package com.demo.lottery.infrastructure.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginRequest {
    @NotNull(message = "must not be null")
    @Size(min = 2, max = 30, message = "must contain from 2 to 30 symbols")
    private String username;
    @Size(min = 2, max = 30, message = "must contain from 2 to 30 symbols")
    private String password;
}
