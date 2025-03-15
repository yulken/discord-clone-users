package com.yulken.discord_clone.users.application.dtos.users;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FindUserRequestDTO {
    private UUID uuid;
    private String login;
    private String email;
    private UUID exceptUuid;
}