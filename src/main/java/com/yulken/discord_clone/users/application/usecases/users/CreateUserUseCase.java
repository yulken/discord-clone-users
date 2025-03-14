package com.yulken.discord_clone.users.application.usecases.users;

import com.yulken.discord_clone.users.application.dtos.users.CreateUserRequestDTO;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase  {

    private final UserPersistencePort userPersistencePort;

    public String execute(CreateUserRequestDTO createUserInput) {
        log.info("Processing createUserInput: {}", createUserInput);

        userPersistencePort.findByEmailOrLogin(createUserInput.getEmail(), createUserInput.getLogin())
                .orElseThrow(() -> {
                    log.error("User already exists");
                    return new ConflictingDataException("Unable to register user");
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
