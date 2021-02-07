package ru.snsin.cakefactory.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.snsin.cakefactory.domain.OrderPlacedEvent;

@Slf4j
@Component
public class OrderReceiver {

    @EventListener
    public void onPlacedOrder(OrderPlacedEvent event) {
        log.info("New order placed!");
        log.info("Delivery address: {}", event.getOrderInfo());
        log.info("Order items: {}", event.getBasketItems());
    }
}

