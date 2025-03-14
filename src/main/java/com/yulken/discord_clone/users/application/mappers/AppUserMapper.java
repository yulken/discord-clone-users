package com.yulken.discord_clone.users.application.mappers;

import com.yulken.discord_clone.users.application.dtos.users.UserDTO;
import com.yulken.discord_clone.users.domain.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    User map(UserDTO dto);

    UserDTO map(User user);

    List<UserDTO> map(List<User> dto);
}
