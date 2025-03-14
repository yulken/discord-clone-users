package com.yulken.discord_clone.users.application.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank
    @Size(min = 2, max = 60)
    private String name;

    @NotBlank
    @Size(min = 2, max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 60)
    private String password;

    @NotBlank
    @Size(min = 2, max = 60)
    private String login;
}
