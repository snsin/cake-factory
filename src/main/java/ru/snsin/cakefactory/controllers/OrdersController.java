package ru.snsin.cakefactory.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.domain.OrderInfo;
import ru.snsin.cakefactory.services.BasketService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final BasketService basketService;

    public OrdersController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    RedirectView placeOrder(@Valid OrderInfo orderInfo) {
        String orderItems = basketService.getNameCountPairs().stream()
                .map(item -> String.format("%s: x%d", item.getName(), item.getCount()))
                .collect(Collectors.joining("\n"));
        basketService.clearBasket();
        log.info("Order items {}", orderItems);
        log.info("Placed order is {}", orderInfo);
        return new RedirectView("/");
    }

}
