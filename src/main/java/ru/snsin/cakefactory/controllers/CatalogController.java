package ru.snsin.cakefactory.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        String principal = getPrincipal();
        Map<String, Object> cakesModel = new HashMap<>();
        cakesModel.put("cakes", catalogService.getAll());
        cakesModel.put("home?", true);
        cakesModel.put("email", principal);
        return new ModelAndView("index", cakesModel);
    }

    private String getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication instanceof AnonymousAuthenticationToken)
                ? null : ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
