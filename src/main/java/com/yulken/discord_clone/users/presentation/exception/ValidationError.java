package com.yulken.discord_clone.users.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationError {
    private String field;
    private String message;
}
