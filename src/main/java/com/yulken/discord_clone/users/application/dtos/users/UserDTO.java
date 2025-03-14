package com.yulken.discord_clone.users.application.dtos.users;

import lombok.Data;

@Data
public class UserDTO {
    private String uuid;
    private String name;
    private String email;
    private String login;
    private String status;
    private String profilePictureUrl;
}

