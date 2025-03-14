package com.yulken.discord_clone.users.application.dtos.users;

import lombok.Data;

import java.util.List;

@Data
public class ListUserResponseDTO {

    private List<UserDTO> users;
}