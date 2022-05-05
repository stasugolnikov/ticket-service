package ru.itmo.uml.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itmo.uml.project.entity.EventEntity;
import ru.itmo.uml.project.repository.EventRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceChangingService {
    private final EventRepository eventRepository;
    private final SimpMessagingTemplate template;

    @Scheduled(cron = "0 */1 * ? * *")
    public void changePrice() {
        List<EventEntity> events = eventRepository.findAll();
        int index = ThreadLocalRandom.current().nextInt(0, events.size());
        EventEntity event = events.get(index);
        int oldPrice = event.getPrice().intValueExact();
        event.setPrice(countNewPrice(event.getPrice()));
        event = eventRepository.save(event);
        template.convertAndSend("/topic/change-price", String.format("Цена на матч \"%s\" изменилась\nОбновите страницу",
                event.getName()));
        log.info("Цена на матч \"{}\" изменилась на {} ₽", event.getName(), Math.abs(oldPrice - event.getPrice().intValueExact()));
    }

    public BigDecimal countNewPrice(BigDecimal price) {
        int delta = ThreadLocalRandom.current().nextInt(100, 200);
        return new Random().nextBoolean() ?
                BigDecimal.valueOf(price.intValueExact() + delta) :
                BigDecimal.valueOf(price.intValueExact() - delta);
    }
}
