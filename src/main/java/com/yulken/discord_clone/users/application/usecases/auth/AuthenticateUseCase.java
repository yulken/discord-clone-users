package com.yulken.discord_clone.users.application.usecases.auth;

import com.yulken.discord_clone.users.application.dtos.auth.AuthResponseDTO;

public interface AuthenticateUseCase {
    AuthResponseDTO execute(String login, String password);
}
