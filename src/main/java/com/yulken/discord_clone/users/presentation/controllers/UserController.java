package com.yulken.discord_clone.users.presentation.controllers;

import com.yulken.discord_clone.users.application.dtos.MessageResponseDTO;
import com.yulken.discord_clone.users.application.dtos.users.CreateUserRequestDTO;
import com.yulken.discord_clone.users.application.dtos.users.CreateUserResponseDTO;
import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.dtos.users.ListUserRequestDTO;
import com.yulken.discord_clone.users.application.dtos.users.ListUserResponseDTO;
import com.yulken.discord_clone.users.application.dtos.users.UserDTO;
import com.yulken.discord_clone.users.application.mappers.AppUserMapper;
import com.yulken.discord_clone.users.application.usecases.users.CreateUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.DeleteUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.FindUserUseCase;
import com.yulken.discord_clone.users.application.usecases.users.ListUserUseCase;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.Token;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AppUserMapper appUserMapper;
    private final JwtUtils jwtUtils;
    private final ListUserUseCase listUsers;
    private final CreateUserUseCase createUser;
    private final FindUserUseCase findUser;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping
    public ResponseEntity<ListUserResponseDTO> index(
            @RequestHeader(name = "Authorization", required = false) String jwt,
            @ModelAttribute ListUserRequestDTO requestDTO) {

        log.info("Fetching users using params: {}", requestDTO);

        FindUserRequestDTO findUserRequestDTO = appUserMapper.mapToFindUser(requestDTO);
        Pagination pagination = appUserMapper.map(requestDTO);

        getSelfUuid(jwt).ifPresent(findUserRequestDTO::setExceptUuid);

        List<User> users = listUsers.execute(findUserRequestDTO, pagination);
        List<UserDTO> usersDTO = appUserMapper.map(users);

        return ResponseEntity.ok(new ListUserResponseDTO(usersDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findByUuid(@PathVariable UUID uuid) {
        log.info("Fetching user by UUID {}", uuid);

        Optional<User> optUser = findUser.execute(uuid);

        return optUser.map(user -> ResponseEntity.ok(appUserMapper.map(user)))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    @PostMapping
    public CreateUserResponseDTO create(@RequestBody CreateUserRequestDTO createUserRequest) {
        log.info("Creating new user with name {}", createUserRequest.getName());

        String uuid = createUser.execute(createUserRequest);
        return new CreateUserResponseDTO(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<MessageResponseDTO> delete(
            @RequestHeader(name = "Authorization") String jwt,
            @PathVariable UUID uuid) {

        log.info("Deleting user with UUID {}", uuid);

        Token token = jwtUtils.decode(jwt.split(" ")[1]);
        boolean isDeleted = deleteUserUseCase.execute(uuid, token);

        if (!isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new MessageResponseDTO("User successfully deleted"));
    }

    private Optional<UUID> getSelfUuid(String jwt){
        if (jwt != null) {
            Token token = jwtUtils.decode(jwt.split(" ")[1]);
            return Optional.of(token.uuid());
        }

        return Optional.empty();
    }
}