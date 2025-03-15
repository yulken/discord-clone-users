package com.yulken.discord_clone.users.infrastructure.http.controllers;

import com.yulken.discord_clone.users.application.dtos.auth.AuthRequestDTO;
import com.yulken.discord_clone.users.application.dtos.auth.AuthResponseDTO;
import com.yulken.discord_clone.users.application.usecases.auth.AuthenticateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUseCase authenticateUseCase;

    @PostMapping
    public AuthResponseDTO auth(@RequestBody AuthRequestDTO authRequest) {
        log.info("User {} attempting login", authRequest.getLogin());
        return authenticateUseCase.execute(authRequest.getLogin(), authRequest.getPassword());
    }
}
