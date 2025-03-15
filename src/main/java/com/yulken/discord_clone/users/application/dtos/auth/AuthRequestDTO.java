package com.yulken.discord_clone.users.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
