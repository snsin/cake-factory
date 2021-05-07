package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.address.AddressService;
import ru.snsin.cakefactory.components.Basket;

import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
    ModelAndView basket(Principal principal){
        Map<String, Object> basketModel = new HashMap<>();
        basketModel.put("basket?", true);
        basketModel.put("basketItems", basket.getBasketItems());
        Address actualAddress = principal != null
                ? addressService.findByUserId(principal.getName()).orElse(Address.EMPTY_ADDRESS)
                : Address.EMPTY_ADDRESS;
        basketModel.put("address", actualAddress);
        return new ModelAndView("basket", basketModel);
    }

    @PostMapping("/delete")
    RedirectView removeItem(@RequestParam @NotEmpty String sku) {
        basket.removeItem(sku);
        return new RedirectView("/basket");
    }
}
