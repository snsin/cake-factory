package ru.snsin.cakefactory.users;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AddressService addressService;

    public AccountController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    ModelAndView getAccountPage(@AuthenticationPrincipal UserDetails principal) {
        Address actualAddress = addressService.findByUserId(principal.getUsername())
                .orElse(Address.EMPTY_ADDRESS);
        Map<String, Object> pageData = Collections.singletonMap("address", actualAddress);
        return new ModelAndView("account", pageData);
    }

    @PostMapping("/update")
    RedirectView updateAddress(@Valid Address address, @AuthenticationPrincipal UserDetails principal) {
        addressService.save(address, principal.getUsername());
        return new RedirectView("/");
    }

}
