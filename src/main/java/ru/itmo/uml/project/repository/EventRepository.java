package ru.itmo.uml.project.repository;

import ru.itmo.uml.project.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findById(Long id);
}
