package com.yulken.discord_clone.users.infrastructure.http;

import com.yulken.discord_clone.users.application.dtos.geolocation.GeolocationResponse;
import com.yulken.discord_clone.users.application.ports.clients.GeolocationClient;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessGeolocation;
import com.yulken.discord_clone.users.infrastructure.http.feign.GeolocationFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeolocationRestClient implements GeolocationClient {
    private final GeolocationFeignClient feignClient;


    @Override
    public AccessGeolocation getGeolocationByIpAddress(String ipAddress) {
        GeolocationResponse geolocationResponse = feignClient.fetchGeolocation(ipAddress);
        return new AccessGeolocation(geolocationResponse.getCountry(), geolocationResponse.getRegion(), geolocationResponse.getCity());
    }
}
