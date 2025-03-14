package com.yulken.discord_clone.users.infrastructure.http.controllers;

import com.yulken.discord_clone.users.application.dtos.users.*;
import com.yulken.discord_clone.users.application.dtos.MessageResponseDTO;
import com.yulken.discord_clone.users.application.mappers.AppUserMapper;
import com.yulken.discord_clone.users.application.usecases.users.CreateUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.DeleteUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.FindUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.ListUserUseCase;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.infrastructure.http.mappers.HttpUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AppUserMapper appUserMapper;
    private final HttpUserMapper httpUserMapper;
    private final ListUserUseCase listUsers;
    private final CreateUserUseCase createUser;
    private final FindUserUseCase findUser;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping
    public ResponseEntity<ListUserResponseDTO> index(
            @RequestHeader(name = "Authorization", required = false) String jwt,
            @ModelAttribute ListUserRequestDTO requestDTO) {

        log.info("Fetching users using params: {}", requestDTO);

        Pagination pagination = httpUserMapper.map(requestDTO);

        if (jwt != null) {
            Token token = new Token(jwt.split(" ")[1]);
            requestDTO.setExceptUuid(token.getUuid().toString());
        }

        List<User> users = listUsers.execute(requestDTO, pagination);
        List<UserDTO> usersDTO = appUserMapper.map(users);

        return ResponseEntity.ok(new ListUserResponseDTO(usersDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findByUuid(@PathVariable String uuid) {
        log.info("Fetching user by UUID {}", uuid);

        Optional<User> optUser = findUser.execute(uuid);

        return optUser.map(user -> ResponseEntity.ok(appUserMapper.map(user)))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> create(@RequestBody CreateUserRequestDTO createUserRequest) {
        log.info("Creating new user with name {}", createUserRequest.getName());

        String uuid = createUser.execute(createUserRequest);
        return ResponseEntity.ok(new CreateUserResponseDTO(uuid));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<MessageResponseDTO> delete(
            @RequestHeader(name = "Authorization") String jwt,
            @PathVariable String uuid) {

        log.info("Deleting user with UUID {}", uuid);

        Token token = new Token(jwt.split(" ")[1]);
        boolean isDeleted = deleteUserUseCase.execute(uuid, token);

        if (!isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new MessageResponseDTO("User successfully deleted"));
    }
}