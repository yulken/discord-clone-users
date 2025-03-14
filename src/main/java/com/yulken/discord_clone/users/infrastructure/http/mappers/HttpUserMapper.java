package com.yulken.discord_clone.users.infrastructure.http.mappers;

import com.yulken.discord_clone.users.application.dtos.users.ListUserRequestDTO;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HttpUserMapper {
    Pagination map(ListUserRequestDTO requestDTO);

}
