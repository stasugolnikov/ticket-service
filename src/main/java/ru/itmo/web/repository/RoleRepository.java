package ru.itmo.web.repository;

import ru.itmo.web.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(@Param("role") String role);
}
