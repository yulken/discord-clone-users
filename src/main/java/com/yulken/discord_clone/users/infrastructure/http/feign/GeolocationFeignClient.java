package com.yulken.discord_clone.users.infrastructure.http.feign;

import com.yulken.discord_clone.users.application.dtos.geolocation.GeolocationResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ip-api",  url = "http://ip-api.com/json")
public interface GeolocationFeignClient {

    @GetMapping("/{ipAddress}")
    GeolocationResponse fetchGeolocation(@PathParam("ipAddress") String ipAddress);

}
