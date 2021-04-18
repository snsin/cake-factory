package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CatalogController {

    private final CakeCatalogService catalogService;

    public CatalogController(CakeCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    ModelAndView getCatalog() {
        Map<String, Object> cakesModel = new HashMap<>();
        cakesModel.put("cakes", catalogService.getAll());
        cakesModel.put("home?", true);
        return new ModelAndView("index", cakesModel);
    }
}
