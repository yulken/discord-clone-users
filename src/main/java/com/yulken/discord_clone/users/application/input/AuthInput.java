package com.yulken.discord_clone.users.application.input;

public record AuthInput(
        String login, String password, String ipAddress, String userAgent
) {
}
