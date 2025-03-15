package com.yulken.discord_clone.users.domain.exceptions;

public class ApiBusinessException extends RuntimeException {
    public ApiBusinessException(String message) { super(message); }
}
