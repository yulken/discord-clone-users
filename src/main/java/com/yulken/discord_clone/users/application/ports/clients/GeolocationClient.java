package com.yulken.discord_clone.users.application.ports.clients;

import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;

public interface GeolocationClient {
    AccessGeolocation getGeolocationByIpAddress(String ipAddress);
}
