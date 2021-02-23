package ru.snsin.cakefactory.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;

@Controller
@RequestMapping("/signup")
@Slf4j
public class SignUpController {

    @GetMapping
    public ModelAndView signup() {
        return new ModelAndView("signup", Collections.singletonMap("login?", true));
    }

    @PostMapping
    public RedirectView signUpNewUser(User user, Address address) {
        log.info("New user signed up with email = {}", user.getEmail());
        log.info("and address = {}", address);
        return new RedirectView("/");
    }
}
