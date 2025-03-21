package com.yulken.discord_clone.users.application.usecases.auth;

import com.yulken.discord_clone.users.application.ports.clients.GeolocationClient;
import com.yulken.discord_clone.users.application.ports.persistence.AccessHistoryPersistencePort;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.utils.JwtUtils;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prd")
public class AuthenticateUseCaseImpl extends AbstractAuthenticateUseCase {

    private final GeolocationClient geolocationClient;

    public AuthenticateUseCaseImpl(
            JwtUtils jwtUtils,
            UserPersistencePort userPersistence,
            AccessHistoryPersistencePort accessHistoryPersistence,
            GeolocationClient geolocationClient
    ) {
        super(jwtUtils, userPersistence, accessHistoryPersistence);
        this.geolocationClient = geolocationClient;
    }


    @Override
    protected void validatePassword(User user, String password){
        user.validatePassword(password);
    }

    @Override
    protected AccessGeolocation getGeolocationByIpAddress(String ipAddress){
        return geolocationClient.getGeolocationByIpAddress(ipAddress);
    }
}