package com.yulken.discord_clone.users.domain.exceptions;

public class ConflictingDataException extends RuntimeException {
    public ConflictingDataException(String message) {
        super(message);
    }
}
