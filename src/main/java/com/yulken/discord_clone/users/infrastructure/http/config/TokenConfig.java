package com.yulken.discord_clone.users.infrastructure.http.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "config.token")
public class TokenConfig {

    private String issuer;
    private String secret;
}
