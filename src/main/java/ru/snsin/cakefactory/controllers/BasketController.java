package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.services.BasketService;

import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    RedirectView addItem(@RequestParam @NotEmpty String cakeName) {
        basketService.addItem(cakeName);
        return new RedirectView("/");
    }
}
