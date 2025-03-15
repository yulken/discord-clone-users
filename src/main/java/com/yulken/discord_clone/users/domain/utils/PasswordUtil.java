package com.yulken.discord_clone.users.domain.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private PasswordUtil() {}

    private static final int COST_FACTOR = 12;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(COST_FACTOR);

    public static String hashPassword(String plaintextPassword) {
        return encoder.encode(plaintextPassword);
    }

    public static boolean isPasswordValid(String plaintextPassword, String hashedPassword) {
        return encoder.matches(plaintextPassword, hashedPassword);
    }
}
