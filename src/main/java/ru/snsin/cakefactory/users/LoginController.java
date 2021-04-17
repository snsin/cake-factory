package ru.snsin.cakefactory.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    public LoginController() {
    }

    @GetMapping("/login")
    ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }
}
