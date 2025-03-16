package com.yulken.discord_clone.users.infrastructure.db.repositories.adapters;

import com.yulken.discord_clone.users.application.ports.persistence.AccessHistoryPersistencePort;
import com.yulken.discord_clone.users.domain.entities.AccessHistory;
import com.yulken.discord_clone.users.infrastructure.db.mappers.RepositoryAccessHistoryMapper;
import com.yulken.discord_clone.users.infrastructure.db.models.AccessHistoryModel;
import com.yulken.discord_clone.users.infrastructure.db.repositories.AccessHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessHistoryRepository implements AccessHistoryPersistencePort {

    private final RepositoryAccessHistoryMapper accessHistoryMapper;
    private final AccessHistoryJpaRepository jpaRepository;

    @Override
    public AccessHistory save(AccessHistory accessHistory) {
        AccessHistoryModel model = accessHistoryMapper.toModel(accessHistory);
        AccessHistoryModel savedModel = jpaRepository.save(model);
        return accessHistoryMapper.toDomain(savedModel);
    }
}
