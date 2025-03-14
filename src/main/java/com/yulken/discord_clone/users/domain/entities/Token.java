package com.yulken.discord_clone.users.domain.entities;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Token {

    public static final int EXPIRATION_HOURS = 1;
    private final String jwtBearer;
    private final LocalDateTime expirationDate;
    private final UUID uuid;

    public Token(User user) {
        this.expirationDate = Date.from(Instant.now().plusSeconds(EXPIRATION_HOURS * 3600));
        this.uuid = user.getUuid();

        Claims claims = Jwts.claims().setSubject(user.getUuid().toString());
        claims.put("name", user.getLogin());
        claims.put("email", user.getEmail());
        claims.put("given_name", user.getName());

        this.jwtBearer = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setIssuer(AppConfig.authApp())
                .signWith(SignatureAlgorithm.HS256, AppConfig.authSecret().getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Token(String bearerJwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(AppConfig.authSecret().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(bearerJwt)
                .getBody();

        this.expirationDate = claims.getExpiration();
        this.uuid = UUID.fromString(claims.getSubject());
        this.jwtBearer = bearerJwt;
    }
}
