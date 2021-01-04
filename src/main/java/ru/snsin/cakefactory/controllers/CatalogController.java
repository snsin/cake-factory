package ru.snsin.cakefactory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {
    @GetMapping("/")
    String getCatalog() {
        return "index";
    }
}
