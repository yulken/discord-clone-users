package com.yulken.discord_clone.users.application.dtos.users;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ListUserRequestDTO {

    private UUID uuid;

    @Size(min = 2)
    private String login;

    @Size(min = 2)
    private String email;

    private String orderBy = "id";
    private String sort = "Desc";

    private int pageSize = 25;
    private int pageCount = 0;
}