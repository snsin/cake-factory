package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.BasketService;
import ru.snsin.cakefactory.services.CakeCatalogService;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;
    private final CakeCatalogService catalogService;

    public BasketController(BasketService basketService, CakeCatalogService catalogService) {
        this.basketService = basketService;
        this.catalogService = catalogService;
    }

    @PostMapping
    RedirectView addItem(@RequestParam @NotEmpty String cakeName) {
        final Optional<CakeItem> addingCake = catalogService.getAll().stream()
                .filter(cakeItem -> cakeItem.getName().equals(cakeName))
                .findAny();
        addingCake.ifPresent(basketService::addItem);
        return new RedirectView("/");
    }

    @GetMapping
    ModelAndView basket(){
        Map<String, Object> basketModel = new HashMap<>();
        basketModel.put("basketItemsCount", basketService.countItems());
        basketModel.put("basket?", true);
        basketModel.put("basketItems", basketService.getNameCountPairs());
        return new ModelAndView("basket", basketModel);
    }
}
