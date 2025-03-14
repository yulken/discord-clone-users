package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindUserUseCase {

    private final UserPersistencePort userPersistencePort;

    public Optional<User> execute(String uuid) {
        return userPersistencePort.findByUuid(uuid);
    }
}
