package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.dtos.users.CreateUserRequestDTO;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.ConflictingDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase  {

    private final UserPersistencePort userPersistencePort;

    public String execute(CreateUserRequestDTO createUserInput) {

        Optional<User> optUser = userPersistencePort.findByEmailOrLogin(createUserInput.getEmail(), createUserInput.getLogin());

        optUser.ifPresent(u -> {
            log.error("User already exists");
            throw new ConflictingDataException("Unable to register user");
        });

        User userModel = new User(
                createUserInput.getName(),
                createUserInput.getEmail(),
                createUserInput.getLogin(),
                createUserInput.getPassword()
        );

        User newUser = userPersistencePort.create(userModel);
        return newUser.getUuid().toString();
    }
}
