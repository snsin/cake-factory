package ru.snsin.cakefactory.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/signup")
@Slf4j
public class SignUpController {

    private final UserService userService;
    private final AddressService addressService;

    public SignUpController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping
    public ModelAndView signup() {
        return new ModelAndView("signup", Collections.singletonMap("login?", true));
    }

    @PostMapping
    public RedirectView signUpNewUser(@Valid User user, @Valid Address address) {
        log.info("New user signed up with email = {}", user.getEmail());
        log.info("and address = {}", address);
        userService.save(user);
        addressService.save(address, user.getEmail());
        return new RedirectView("/");
    }
}
