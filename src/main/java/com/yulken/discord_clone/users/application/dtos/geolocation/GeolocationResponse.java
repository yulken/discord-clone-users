package com.yulken.discord_clone.users.application.dtos.geolocation;

import lombok.Data;

@Data
public class GeolocationResponse {
    private String country;
    private String region;
    private String city;
}
