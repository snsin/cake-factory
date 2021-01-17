package ru.snsin.cakefactory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.domain.OrderInfo;
import ru.snsin.cakefactory.components.Basket;
import ru.snsin.cakefactory.domain.OrderPlacedEvent;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final Basket basket;
    private final ApplicationEventPublisher eventPublisher;

    public OrdersController(Basket basket, ApplicationEventPublisher eventPublisher) {
        this.basket = basket;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    RedirectView placeOrder(@Valid OrderInfo orderInfo) {
        OrderPlacedEvent placedOrder = new OrderPlacedEvent(orderInfo, this.basket.getBasketItems());
        eventPublisher.publishEvent(placedOrder);
        basket.clearBasket();
        return new RedirectView("/");
    }

}
