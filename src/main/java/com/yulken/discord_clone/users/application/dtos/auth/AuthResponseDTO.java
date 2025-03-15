package com.yulken.discord_clone.users.application.dtos.auth;

import java.time.LocalDateTime;

public record AuthResponseDTO(String authorization, LocalDateTime expiresAt) {
}