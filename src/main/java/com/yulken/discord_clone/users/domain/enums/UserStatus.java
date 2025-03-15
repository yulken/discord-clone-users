package com.yulken.discord_clone.users.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("Active", "A"),
    DELETED("Deleted", "D"),
    PENDING_EMAIL("Pending Email", "P");

    private final String name;
    private final String shortName;

    private static final Map<String, UserStatus> SHORT_NAME_TO_ENUM = new HashMap<>();

    static {
        for (UserStatus status : values()) {
            SHORT_NAME_TO_ENUM.put(status.shortName, status);
        }
    }

    public static UserStatus fromShortString(String shortStr) {
        if (!SHORT_NAME_TO_ENUM.containsKey(shortStr)) {
            throw new IllegalArgumentException("Invalid short name: " + shortStr);
        }
        return SHORT_NAME_TO_ENUM.get(shortStr);
    }
}