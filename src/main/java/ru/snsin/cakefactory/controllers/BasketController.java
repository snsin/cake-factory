package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.services.BasketService;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    RedirectView addItem(@RequestParam @NotEmpty String sku) {
        basketService.addItem(sku);
        return new RedirectView("/");
    }

    @GetMapping
    ModelAndView basket(){
        Map<String, Object> basketModel = new HashMap<>();
        basketModel.put("basketItemsCount", basketService.countItems());
        basketModel.put("basket?", true);
        basketModel.put("basketItems", basketService.getBasketItems());
        return new ModelAndView("basket", basketModel);
    }

    @PostMapping("/delete")
    RedirectView removeItem(@RequestParam @NotEmpty String sku) {
        basketService.removeItem(sku);
        return new RedirectView("/basket");
    }
}
