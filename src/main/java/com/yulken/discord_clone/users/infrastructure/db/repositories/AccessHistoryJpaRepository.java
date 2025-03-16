package com.yulken.discord_clone.users.infrastructure.db.repositories;

import com.yulken.discord_clone.users.infrastructure.db.models.AccessHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessHistoryJpaRepository extends JpaRepository<AccessHistoryModel, Long> {
}
