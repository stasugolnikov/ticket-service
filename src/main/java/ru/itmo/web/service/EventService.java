package ru.itmo.web.service;

import ru.itmo.web.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventService {

    Optional<EventEntity> findById(Long id);

    Page<EventEntity> findAllEventsPageable(Pageable pageable);

}
