package com.demo.lottery.infrastructure.in.web;

import com.demo.lottery.domain.service.AuthenticationService;
import com.demo.lottery.infrastructure.in.web.dto.request.UserLoginRequest;
import com.demo.lottery.infrastructure.in.web.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserLoginRequest userLoginRequest){
        authenticationService.registerUser(userDtoMapper.map(userLoginRequest));
    }
}
