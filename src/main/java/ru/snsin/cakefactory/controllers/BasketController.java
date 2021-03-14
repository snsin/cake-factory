package ru.snsin.cakefactory.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.components.Basket;
import ru.snsin.cakefactory.users.Address;
import ru.snsin.cakefactory.users.AddressService;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final Basket basket;
    private final AddressService addressService;

    public BasketController(Basket basket, AddressService addressService) {
        this.basket = basket;
        this.addressService = addressService;
    }

    @PostMapping
    RedirectView addItem(@RequestParam @NotEmpty String sku) {
        basket.addItem(sku);
        return new RedirectView("/");
    }

    @GetMapping
    ModelAndView basket(@AuthenticationPrincipal String principal){
        Map<String, Object> basketModel = new HashMap<>();
        basketModel.put("basketItemsCount", basket.countItems());
        basketModel.put("basket?", true);
        basketModel.put("basketItems", basket.getBasketItems());
        basketModel.put("address", addressService.findByUserId(principal).orElse(Address.EMPTY_ADDRESS));
        return new ModelAndView("basket", basketModel);
    }

    @PostMapping("/delete")
    RedirectView removeItem(@RequestParam @NotEmpty String sku) {
        basket.removeItem(sku);
        return new RedirectView("/basket");
    }
}
