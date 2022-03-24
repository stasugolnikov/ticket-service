package ru.itmo.web.service;

import ru.itmo.web.exception.NotEnoughTicketsInStockException;
import ru.itmo.web.entity.EventEntity;

import java.math.BigDecimal;
import java.util.Map;

public interface OrderService {

    void addTicket(EventEntity eventEntity);

    void removeTicket(EventEntity eventEntity);

    Map<EventEntity, Integer> getEventsInOrder();

    void checkout() throws NotEnoughTicketsInStockException;

    BigDecimal getTotal();
}
