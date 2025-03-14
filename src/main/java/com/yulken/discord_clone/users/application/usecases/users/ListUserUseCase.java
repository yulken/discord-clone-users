package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUserUseCase {

    private final UserPersistencePort userPersistencePort;

    public List<User> execute(FindUserRequestDTO input, Pagination pagination) {
        return userPersistencePort.findAll(input, pagination);
    }
}