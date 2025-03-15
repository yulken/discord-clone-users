package com.yulken.discord_clone.users.application.ports.persistence;

import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    User create(User user);

    Optional<User> find(FindUserRequestDTO input);

    List<User> findAll(FindUserRequestDTO input, Pagination pagination);

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByEmailOrLogin(String email, String login);

    Optional<User> update(UUID uuid, User user);
}