package com.yulken.discord_clone.users.presentation.controllers;

import com.yulken.discord_clone.users.application.dtos.auth.AuthRequestDTO;
import com.yulken.discord_clone.users.application.dtos.auth.AuthResponseDTO;
import com.yulken.discord_clone.users.application.input.AuthInput;
import com.yulken.discord_clone.users.application.usecases.auth.AbstractAuthenticateUseCase;
import jakarta.servlet.http.HttpServletRequest;
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

    private final AbstractAuthenticateUseCase authenticateUseCase;

    @PostMapping
    public AuthResponseDTO auth(HttpServletRequest request, @RequestBody AuthRequestDTO authRequest) {
        log.info("User {} attempting login", authRequest.getLogin());
        AuthInput input = new AuthInput(authRequest.getLogin(), authRequest.getPassword(), request.getRemoteAddr(), request.getHeader("User-Agent"));
        return authenticateUseCase.execute(input);
    }
}
