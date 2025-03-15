package com.yulken.discord_clone.users.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SortByEnum {

    ASC("Asc"),
    DESC("Desc");

    private final String value;

    private static final Map<String, SortByEnum> VALUE_NAME_TO_ENUM = new HashMap<>();

    static {
        for (SortByEnum sort : values()) {
            VALUE_NAME_TO_ENUM.put(sort.value, sort);
        }
    }

    public static SortByEnum fromShortString(String shortStr) {
        if (!VALUE_NAME_TO_ENUM.containsKey(shortStr)) {
            throw new IllegalArgumentException("Invalid sort name: " + shortStr);
        }
        return VALUE_NAME_TO_ENUM.get(shortStr);
    }
}