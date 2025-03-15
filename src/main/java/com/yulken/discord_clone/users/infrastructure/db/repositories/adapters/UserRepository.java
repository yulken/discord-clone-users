package com.yulken.discord_clone.users.infrastructure.db.repositories.adapters;

import com.yulken.discord_clone.users.application.dtos.users.FindUserRequestDTO;
import com.yulken.discord_clone.users.application.ports.persistence.UserPersistencePort;
import com.yulken.discord_clone.users.domain.entities.Pagination;
import com.yulken.discord_clone.users.domain.entities.User;
import com.yulken.discord_clone.users.domain.enums.SortByEnum;
import com.yulken.discord_clone.users.domain.enums.UserStatus;
import com.yulken.discord_clone.users.infrastructure.db.models.UserModel;
import com.yulken.discord_clone.users.infrastructure.db.repositories.UserJpaRepository;
import com.yulken.discord_clone.users.infrastructure.db.mappers.RepositoryUserMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;
    private final RepositoryUserMapper repositoryUserMapper;

    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return userJpaRepository.findByUuidAndStatus(uuid, UserStatus.ACTIVE.getShortName())
                .map(repositoryUserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailOrLogin(String email, String login) {
        return userJpaRepository.findByEmailOrLoginAndStatus(email, login, UserStatus.ACTIVE.getShortName())
                .map(repositoryUserMapper::toDomain);
    }

    @Override
    public Optional<User> find(FindUserRequestDTO input) {
        Pagination pagination = new Pagination();
        pagination.setPageSize(1);
        pagination.setPageCount(0);
        pagination.setSort(SortByEnum.DESC);

        return findAll(input, pagination).stream().findFirst();
    }

    @Override
    public List<User> findAll(FindUserRequestDTO input, Pagination pagination) {


        Specification<UserModel> spec = createSpecification(input);
        Sort sort = pagination.getSort() == SortByEnum.DESC
                ? Sort.by(Sort.Direction.DESC, pagination.getOrderBy())
                : Sort.by(Sort.Direction.ASC, pagination.getOrderBy());

        Pageable pageable = PageRequest.of(pagination.getPageCount(), pagination.getPageSize(), sort);

        Page<UserModel> userPage = userJpaRepository.findAll(spec, pageable);

        return userPage.stream().map(repositoryUserMapper::toDomain).toList();
    }

    private Specification<UserModel> createSpecification(FindUserRequestDTO input){
        return (user, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(user.get("status"), UserStatus.ACTIVE.getShortName()));

            if (input.getUuid() != null) {
                predicates.add(cb.equal(user.get("uuid"), input.getUuid().toString()));
            }
            if (input.getExceptUuid() != null) {
                predicates.add(cb.notEqual(user.get("uuid"), input.getExceptUuid().toString()));
            }
            if (input.getEmail() != null) {
                predicates.add(cb.equal(user.get("email"), input.getEmail()));
            }
            if (input.getLogin() != null) {
                predicates.add(cb.equal(user.get("login"), input.getLogin()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    @Transactional
    public User create(User user) {
        UserModel userModel = repositoryUserMapper.toModel(user);
        userJpaRepository.save(userModel);
        return repositoryUserMapper.toDomain(userModel);
    }

    @Override
    @Transactional
    public Optional<User> update(UUID uuid, User updatedProps) {
        return userJpaRepository.findByUuidAndStatus(uuid, UserStatus.ACTIVE.getShortName())
                .map(existingUser -> {
                    repositoryUserMapper.update(existingUser, updatedProps);
                    userJpaRepository.save(existingUser);
                    return repositoryUserMapper.toDomain(existingUser);
                });
    }
}