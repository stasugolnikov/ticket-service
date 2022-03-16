package ru.itmo.uml.project.repository;

import ru.itmo.uml.project.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(@Param("role") String role);
}
