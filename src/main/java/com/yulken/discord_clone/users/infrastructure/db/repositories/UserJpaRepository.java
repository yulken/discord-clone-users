package com.yulken.discord_clone.users.infrastructure.db.repositories;

import com.yulken.discord_clone.users.infrastructure.db.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserModel, Long>, JpaSpecificationExecutor<UserModel> {
    Optional<UserModel> findByUuidAndStatus(UUID uuid, String status);

    @Query("SELECT u FROM UserModel u WHERE ( u.email = :email or u.login = :login ) and status = :status ")
    Optional<UserModel> findByEmailOrLoginAndStatus(String email, String login, String status);

}
