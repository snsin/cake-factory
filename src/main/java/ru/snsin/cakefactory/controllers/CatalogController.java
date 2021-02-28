package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.snsin.cakefactory.components.Basket;
import ru.snsin.cakefactory.services.CakeCatalogService;
import ru.snsin.cakefactory.users.SignUp;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CatalogController {

    private final CakeCatalogService catalogService;
    private final Basket basket;
    private final SignUp signUp;

    public CatalogController(CakeCatalogService catalogService, Basket basket, SignUp signUp) {
        this.catalogService = catalogService;
        this.basket = basket;
        this.signUp = signUp;
    }

    @GetMapping("/")
    ModelAndView getCatalog() {
        Map<String, Object> cakesModel = new HashMap<>();
        cakesModel.put("cakes", catalogService.getAll());
        cakesModel.put("basketItemsCount", basket.countItems());
        cakesModel.put("home?", true);
        cakesModel.put("email", signUp.getEmail());
        return new ModelAndView("index", cakesModel);
    }
}
