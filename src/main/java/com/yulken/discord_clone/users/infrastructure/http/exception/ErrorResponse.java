package com.yulken.discord_clone.users.infrastructure.http.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {
    private int httpStatus;
    private String message;
    private List<ValidationError> errors;
    private String datetime = LocalDateTime.now().toString();

    public ErrorResponse(HttpStatus httpStatus, String message, List<ValidationError> errors) {
        this.httpStatus = httpStatus.value();
        this.message = message;
        this.errors = errors;
    }
    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus.value();
        this.message = message;
    }
}
