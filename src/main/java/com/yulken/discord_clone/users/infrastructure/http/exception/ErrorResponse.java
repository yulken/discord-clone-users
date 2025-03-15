package com.yulken.discord_clone.users.infrastructure.http.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(int httpStatus, String message, List<ValidationError> errors, String datetime
) {

    public ErrorResponse(HttpStatus httpStatus, String message, List<ValidationError> errors) {
        this(httpStatus.value(), message, errors, LocalDateTime.now().toString());
    }
    public ErrorResponse(HttpStatus httpStatus, String message) {
        this(httpStatus.value(), message, null, LocalDateTime.now().toString());
    }
}
