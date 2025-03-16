package com.yulken.discord_clone.users.application.usecases.auth;

import com.yulken.discord_clone.users.application.ports.clients.GeolocationClient;
import com.yulken.discord_clone.users.application.ports.persistence.AccessHistoryPersistencePort;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import com.yulken.discord_clone.users.domain.utils.JwtUtils;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prd")
public class MockAbstractAuthenticateUseCase extends AbstractAuthenticateUseCase {
    private static final String MOCK_IP = "189.79.144.150";
    private static final String MOCK_PASSWORD = "001122";
    private final GeolocationClient geolocationClient;

    public MockAbstractAuthenticateUseCase(JwtUtils jwtUtils, UserPersistencePort userPersistence, AccessHistoryPersistencePort accessHistoryPersistence, GeolocationClient geolocationClient) {
        super(jwtUtils, userPersistence, accessHistoryPersistence);
        this.geolocationClient = geolocationClient;
    }


    @Override
    protected void validatePassword(User user, String password){
        if (!password.equals(MOCK_PASSWORD)){
            throw new UnauthorizedException("Login ou senha são inválidos");
        }
    }

    @Override
    protected AccessGeolocation getGeolocationByIpAddress(String ipAddress){
        return geolocationClient.getGeolocationByIpAddress(MOCK_IP);
    }

}
