package ru.itmo.web.service;

import ru.itmo.web.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    UserEntity saveUser(UserEntity userEntity);

}
