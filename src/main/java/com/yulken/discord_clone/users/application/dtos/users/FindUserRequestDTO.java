package com.yulken.discord_clone.users.application.dtos.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserRequestDTO {
    private String uuid;
    private String login;
    private String email;
    private String exceptUuid;
}