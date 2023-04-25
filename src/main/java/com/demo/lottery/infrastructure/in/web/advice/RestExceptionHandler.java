package com.demo.lottery.infrastructure.in.web.advice;

import com.demo.lottery.domain.exception.LotteryFinishedException;
import com.demo.lottery.domain.exception.LotteryNotFoundException;
import com.demo.lottery.domain.exception.UsernameTakenException;
import com.demo.lottery.infrastructure.in.web.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(UsernameTakenException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleUsernameTakenException(UsernameTakenException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.toString(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
    }

    @ExceptionHandler(LotteryFinishedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLotteryFinishedException(LotteryFinishedException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
    }

    @ExceptionHandler(LotteryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleLotteryNotFoundException(LotteryNotFoundException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());

        var response = new StringBuilder();
        var first = true;

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {

            if (!first) {
                response.append("; ");
            }

            response.append("field: '").append(((FieldError) error).getField()).append("' ")
                    .append(error.getDefaultMessage());
            first = false;
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), response.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("Caught unusual exception: ", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
    }
}
