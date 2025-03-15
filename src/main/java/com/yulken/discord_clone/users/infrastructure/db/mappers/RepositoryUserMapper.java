package com.yulken.discord_clone.users.infrastructure.db.mappers;

import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.enums.UserStatus;
import com.yulken.discord_clone.users.infrastructure.db.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RepositoryUserMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    User toDomain(UserModel model);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    UserModel toModel(User domain);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    void update(@MappingTarget UserModel model, User domain);

    @Named("mapStatus")
    default String mapStatus(UserStatus status){
        return status.getShortName();
    }

    @Named("mapStatus")
    default UserStatus mapStatus(String status){
        if (status == null){
            return null;
        }
        return UserStatus.fromShortString(status);
    }

}
