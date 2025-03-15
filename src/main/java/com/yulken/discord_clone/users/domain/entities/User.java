package com.yulken.discord_clone.users.domain.entities;

import com.yulken.discord_clone.users.domain.annotations.Default;
import com.yulken.discord_clone.users.domain.enums.UserStatus;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import com.yulken.discord_clone.users.domain.utils.PasswordUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Data
@Setter(AccessLevel.NONE)
public class User {

    private Long id;
    private UUID uuid;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserStatus status;
    private String profilePictureUrl;
    private LocalDateTime deletedAt;

    public User(String name, String email, String login, String plaintextPassword) {
        if (Stream.of(name, email, login, plaintextPassword).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.uuid = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.login = login;
        this.status = UserStatus.ACTIVE;
        this.password = PasswordUtil.hashPassword(plaintextPassword);
    }

    @Default
    public User(Long id,UUID uuid, String name, String email, String login, String password, UserStatus status, String profilePictureUrl) {
        if (Stream.of(id, uuid, name, email, login, password, status).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.status = status;
        this.profilePictureUrl = profilePictureUrl;
    }

    public void activate() {
        status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        status = UserStatus.DELETED;
        deletedAt = LocalDateTime.now();
    }

    public void validatePassword(String plaintextPassword) {
        if (!PasswordUtil.isPasswordValid(plaintextPassword, this.password)){
            throw new UnauthorizedException("Login ou senha são inválidos");
        }
    }
}