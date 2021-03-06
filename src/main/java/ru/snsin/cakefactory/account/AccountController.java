package ru.snsin.cakefactory.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.address.AddressService;

import javax.validation.Valid;
import java.security.Principal;
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
    ModelAndView getAccountPage(Principal principal) {
        Address actualAddress = addressService.findByUserId(principal.getName())
                .orElse(Address.EMPTY_ADDRESS);
        Map<String, Object> pageData = Collections.singletonMap("address", actualAddress);
        return new ModelAndView("account", pageData);
    }

    @PostMapping("/update")
    RedirectView updateAddress(@Valid Address address, Principal principal) {
        addressService.save(address, principal.getName());
        return new RedirectView("/");
    }

}
