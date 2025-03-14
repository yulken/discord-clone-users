package com.yulken.discord_clone.users.application.ports.persistence;

import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {

    User create(User user);

    List<User> findAll(FindUserRequestDTO input, Pagination pagination);

    Optional<User> findById(Long id);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmailOrLogin(String email, String login);

    Optional<User> update(String uuid, User user);

    Optional<User> findWithLoginAndPassword(String login, String password);
}