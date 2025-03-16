package com.yulken.discord_clone.users.infrastructure.db.mappers;

import com.yulken.discord_clone.users.domain.entities.AccessHistory;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessHistoryModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RepositoryUserMapper.class)
public interface RepositoryAccessHistoryMapper {

    @Mapping(target = "geolocation.country", source = "country")
    @Mapping(target = "geolocation.city", source = "city")
    @Mapping(target = "geolocation.region", source = "region")
    AccessHistory toDomain(AccessHistoryModel model);

    @InheritInverseConfiguration
    AccessHistoryModel toModel(AccessHistory domain);
}
