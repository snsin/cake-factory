package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    private final CakeCatalogService catalogService;

    public CatalogController(CakeCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    ModelAndView getCatalog() {
        Map<String, List<CakeItem>> cakesModel =
                Collections.singletonMap("cakes", catalogService.getAll());
        return new ModelAndView("index", cakesModel);
    }
}
