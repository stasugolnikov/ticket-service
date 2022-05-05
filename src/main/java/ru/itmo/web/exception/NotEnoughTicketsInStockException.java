package ru.itmo.web.exception;

import ru.itmo.web.entity.EventEntity;

public class NotEnoughTicketsInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough tickets in stock";

    public NotEnoughTicketsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughTicketsInStockException(EventEntity eventEntity) {
        super(String.format("Not enough %s tickets in stock. Only %d left", eventEntity.getName(), eventEntity.getQuantity()));
    }

}
