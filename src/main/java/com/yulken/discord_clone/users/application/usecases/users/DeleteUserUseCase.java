package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserPersistencePort userPersistencePort;

    public boolean execute(String uuid, Token token) {

        if (!token.getUuid().toString().equals(uuid)) {
            throw new UnauthorizedException("Logged user differs from user to be deleted");
        }

        Optional<User> optUser = userPersistencePort.findByUuid(uuid);
        if (optUser.isEmpty()) {
            return false;
        }

        User user = optUser.get();

        user.deactivate();
        userPersistencePort.update(uuid, user);
        return true;
    }
}
