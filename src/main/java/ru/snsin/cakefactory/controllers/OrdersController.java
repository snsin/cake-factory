package ru.snsin.cakefactory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.domain.OrderInfo;
import ru.snsin.cakefactory.components.Basket;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final Basket basket;

    public OrdersController(Basket basket) {
        this.basket = basket;
    }

    @PostMapping
    RedirectView placeOrder(@Valid OrderInfo orderInfo) {
        String orderItems = basket.getBasketItems().stream()
                .map(item -> String.format("%s: x%d", item.getCake().getName(), item.getCount()))
                .collect(Collectors.joining("\n"));
        basket.clearBasket();
        log.info("Order items {}", orderItems);
        log.info("Placed order is {}", orderInfo);
        return new RedirectView("/");
    }

}
