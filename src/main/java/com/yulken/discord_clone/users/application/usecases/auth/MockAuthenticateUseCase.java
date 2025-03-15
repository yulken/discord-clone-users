package com.yulken.discord_clone.users.application.usecases.auth;

import com.yulken.discord_clone.users.application.dtos.auth.AuthResponseDTO;
import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.ConflictingDataException;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import com.yulken.discord_clone.users.domain.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Optional;

@Service
@Profile("!prd")
@RequiredArgsConstructor
public class MockAuthenticateUseCase implements AuthenticateUseCase{
    private final UserPersistencePort userPersistence;
    private final JwtUtils jwtUtils;

    public AuthResponseDTO execute(String login, String password) {
        User user = findUser(login);
        if (!password.equals("001122")){
            throw new UnauthorizedException("Login ou senha são inválidos");
        }

        Token token = jwtUtils.encode(user);
        return new AuthResponseDTO(token.jwtBearer(), token.expirationDate().atZone(ZoneOffset.UTC).toLocalDateTime());
    }

    private User findUser(String login) {
        FindUserRequestDTO input = new FindUserRequestDTO();
        input.setLogin(login);

        Optional<User> user = userPersistence.find(input);
        if (user.isEmpty()) {
            throw new ConflictingDataException("Login ou senha são inválidos");
        }

        return user.get();
    }
}
