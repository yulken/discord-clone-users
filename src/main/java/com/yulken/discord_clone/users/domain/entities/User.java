package com.yulken.discord_clone.users.domain.entities;

import com.yulken.discord_clone.users.domain.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class User {

    private UUID uuid;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserStatus status;
    private String profilePictureUrl;
    private Long deletedAt;

    public User(String name, String email, String login, String plaintextPassword) {
        if (Stream.of(name, email, login, plaintextPassword).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.uuid = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.login = login;
        this.status = UserStatus.ACTIVE;
        this.password = hashPassword(plaintextPassword);
    }

    public User(UUID uuid, String name, String email, String login, String encryptedPassword, UserStatus status, String profilePictureUrl) {
        if (Stream.of(uuid, name, email, login, encryptedPassword, status, profilePictureUrl).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = encryptedPassword;
        this.status = status;
        this.profilePictureUrl = profilePictureUrl;
    }

    public void activate() {
        status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        status = UserStatus.DELETED;
        deletedAt = System.currentTimeMillis();
    }

    public boolean isSelf(UUID uuid) {
        return this.uuid.equals(uuid);
    }

    public boolean isPasswordCorrect(String plaintextPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plaintextPassword, password);
    }

    private String hashPassword(String plaintextPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plaintextPassword);
    }
}