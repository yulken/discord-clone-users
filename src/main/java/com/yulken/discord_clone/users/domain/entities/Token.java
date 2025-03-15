package com.yulken.discord_clone.users.domain.entities;

import java.time.Instant;
import java.util.UUID;

public record Token(String jwtBearer, Instant expirationDate, UUID uuid) {

    public boolean isSelf(UUID uuid) {
        return this.uuid.equals(uuid);
    }
}
