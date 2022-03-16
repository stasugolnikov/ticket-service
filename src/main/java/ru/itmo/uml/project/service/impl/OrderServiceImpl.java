package ru.itmo.uml.project.service.impl;

import ru.itmo.uml.project.exception.NotEnoughTicketsInStockException;
import ru.itmo.uml.project.entity.EventEntity;
import ru.itmo.uml.project.repository.EventRepository;
import ru.itmo.uml.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class OrderServiceImpl implements OrderService {

    private final EventRepository eventRepository;

    private final Map<EventEntity, Integer> tickets = new ConcurrentHashMap<>();

    @Autowired
    public OrderServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addTicket(EventEntity eventEntity) {
        if (tickets.containsKey(eventEntity)) {
            tickets.replace(eventEntity, tickets.get(eventEntity) + 1);
        } else {
            tickets.put(eventEntity, 1);
        }
    }

    @Override
    public void removeTicket(EventEntity eventEntity) {
        if (tickets.containsKey(eventEntity)) {
            if (tickets.get(eventEntity) > 1)
                tickets.replace(eventEntity, tickets.get(eventEntity) - 1);
            else if (tickets.get(eventEntity) == 1) {
                tickets.remove(eventEntity);
            }
        }
    }

    @Override
    public Map<EventEntity, Integer> getEventsInOrder() {
        return tickets;
    }

    @Override
    public void checkout() throws NotEnoughTicketsInStockException {
        EventEntity eventEntity;
        for (Map.Entry<EventEntity, Integer> entry : tickets.entrySet()) {
            eventEntity = eventRepository.findOne(entry.getKey().getId());
            if (eventEntity.getQuantity() < entry.getValue())
                throw new NotEnoughTicketsInStockException(eventEntity);
            entry.getKey().setQuantity(eventEntity.getQuantity() - entry.getValue());
        }
        eventRepository.save(tickets.keySet());
        eventRepository.flush();
        tickets.clear();
    }

    @Override
    public BigDecimal getTotal() {
        return tickets.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
