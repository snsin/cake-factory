package ru.snsin.cakefactory.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.snsin.cakefactory.components.Basket;

import java.util.Collections;
import java.util.Map;

@Controller
public class LoginController {

    private final Basket basket;

    public LoginController(Basket basket) {
        this.basket = basket;
    }

    @GetMapping("/login")
    ModelAndView getLoginPage() {
        Map<String, Object> basketItemsCount = Collections.singletonMap("basketItemsCount", basket.countItems());
        return new ModelAndView("login", basketItemsCount);
    }
}
