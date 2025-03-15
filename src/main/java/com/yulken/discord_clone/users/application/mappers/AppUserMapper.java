package com.yulken.discord_clone.users.application.mappers;

import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.dtos.users.ListUserRequestDTO;
import com.yulken.discord_clone.users.application.dtos.users.UserDTO;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.enums.SortByEnum;
import com.yulken.discord_clone.users.domain.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    UserDTO map(User user);

    List<UserDTO> map(List<User> users);

    @Mapping(target = "sort", source = "sort", qualifiedByName = "mapSort")
    Pagination map(ListUserRequestDTO requestDTO);

    @Mapping(target = "exceptUuid", ignore = true)
    FindUserRequestDTO mapToFindUser(ListUserRequestDTO requestDTO);

    @Named("mapStatus")
    default String mapStatus(UserStatus status){
        return status.getName();
    }

    @Named("mapSort")
    default SortByEnum mapSort(String sortBy){
        return SortByEnum.fromShortString(sortBy);
    }
}
