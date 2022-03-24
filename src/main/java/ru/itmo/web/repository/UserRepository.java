package ru.itmo.web.repository;

import ru.itmo.web.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(@Param("email") String email);

    Optional<UserEntity> findByUsername(@Param("username") String username);
}
