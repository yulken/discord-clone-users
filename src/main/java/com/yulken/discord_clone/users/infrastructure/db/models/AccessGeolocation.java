package com.yulken.discord_clone.users.infrastructure.db.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class AccessGeolocation {
    private String country;
    private String region;
    private String city;

    public AccessGeolocation(String country, String region, String city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }
}
