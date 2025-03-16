package com.yulken.discord_clone.users.domain.entities;

import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class AccessHistory {

    private Long id;
    private String ipAddress;
    private AccessGeolocation geolocation;
    private String userAgent;
    private Boolean succeeded;
    private User user;

    public AccessHistory(String ipAddress, AccessGeolocation geolocation, String userAgent, Boolean succeeded, User user) {
        this.ipAddress = ipAddress;
        this.geolocation = geolocation;
        this.userAgent = userAgent;
        this.succeeded = succeeded;
        this.user = user;
    }
}
