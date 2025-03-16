package com.yulken.discord_clone.users.domain.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import com.yulken.discord_clone.users.presentation.config.TokenConfig;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class JwtUtils {

    private static final int EXPIRATION_HOURS = 1;
    private static final ZoneOffset zoneOffset = ZoneOffset.ofHours(-3);
    private final TokenConfig config;
    private final Algorithm algorithm;

    public JwtUtils(TokenConfig config) {
        this.config = config;
        this.algorithm = Algorithm.HMAC256(config.getSecret());
    }

    public Token encode(User user) {
        Instant expirationDate = LocalDateTime.now()
                .plusHours(EXPIRATION_HOURS)
                .toInstant(zoneOffset);

        String bearerJwt = JWT.create()
                .withIssuedAt(LocalDateTime.now().toInstant(zoneOffset))
                .withExpiresAt(expirationDate)
                .withIssuer(config.getIssuer())
                .withSubject(user.getUuid().toString())
                .withClaim("name", user.getLogin())
                .withClaim("email", user.getEmail())
                .withClaim("given_name", user.getName())
                .sign(algorithm);

        return new Token(bearerJwt, expirationDate, user.getUuid());
    }

    public Token decode(String bearerJwt) {
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(bearerJwt);

            DecodedJWT decodedJWT = JWT.decode(bearerJwt);
            return new Token(bearerJwt, decodedJWT.getExpiresAtAsInstant(), UUID.fromString(decodedJWT.getSubject()));
        } catch (SignatureVerificationException ex){
          throw new UnauthorizedException("Token inválido");
        }
    }
}
