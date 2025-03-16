package com.yulken.discord_clone.users.application.usecases.auth;

import com.yulken.discord_clone.users.application.dtos.auth.AuthResponseDTO;
import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.input.AuthInput;
import com.yulken.discord_clone.users.application.ports.persistence.AccessHistoryPersistencePort;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.AccessHistory;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import com.yulken.discord_clone.users.domain.utils.JwtUtils;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractAuthenticateUseCase {
    private final JwtUtils jwtUtils;
    private final UserPersistencePort userPersistence;
    private final AccessHistoryPersistencePort accessHistoryPersistence;


    @Transactional(rollbackFor = Throwable.class, noRollbackFor = UnauthorizedException.class)
    public AuthResponseDTO execute(AuthInput input) {
        User user = findUser(input.login());
        try {
            validatePassword(user, input.password());

            Token token = jwtUtils.encode(user);
            auditAccessHistory(input.ipAddress(), input.userAgent(), user, true);
            return new AuthResponseDTO(token.jwtBearer(), token.expirationDate().atZone(ZoneOffset.UTC).toLocalDateTime());
        }  catch (UnauthorizedException e){
            auditAccessHistory(input.ipAddress(), input.userAgent(), user, false);
            throw e;
        }
    }

    protected User findUser(String login) {
        FindUserRequestDTO input = new FindUserRequestDTO();
        input.setLogin(login);

        Optional<User> user = userPersistence.find(input);
        if (user.isEmpty()) {
            throw new UnauthorizedException("Login ou senha são inválidos");
        }

        return user.get();
    }

    protected void auditAccessHistory(String ipAddress, String userAgent, User user, Boolean succeeded){
        AccessGeolocation geolocationByIpAddress = getGeolocationByIpAddress(ipAddress);
        AccessHistory history = new AccessHistory(ipAddress, geolocationByIpAddress, userAgent, succeeded, user);
        accessHistoryPersistence.save(history);
    }

    protected abstract void validatePassword(User user, String password);

    protected abstract AccessGeolocation getGeolocationByIpAddress(String ipAddress);

}
