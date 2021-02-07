package ru.snsin.cakefactory.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class SignUpController {

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("signup", Collections.singletonMap("login?", true));
    }
}
