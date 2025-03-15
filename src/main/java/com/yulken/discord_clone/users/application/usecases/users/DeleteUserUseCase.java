package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserPersistencePort userPersistencePort;

    public boolean execute(UUID uuid, Token token) {

        if (!token.isSelf(uuid)) {
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
