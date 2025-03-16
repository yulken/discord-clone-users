package com.yulken.discord_clone.users.application.ports.persistence;

import com.yulken.discord_clone.users.domain.entities.AccessHistory;

public interface AccessHistoryPersistencePort {
    AccessHistory save(AccessHistory accessHistory);
}
