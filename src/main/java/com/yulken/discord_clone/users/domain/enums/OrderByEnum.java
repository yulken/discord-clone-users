package com.yulken.discord_clone.users.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderByEnum {
    ASC("Asc"),
    DESC("Desc");

    private final String value;
}