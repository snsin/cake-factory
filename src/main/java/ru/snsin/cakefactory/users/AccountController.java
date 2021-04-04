package ru.snsin.cakefactory.users;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.components.Basket;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final Basket basket;
    private final AddressService addressService;

    public AccountController(Basket basket, AddressService addressService) {
        this.basket = basket;
        this.addressService = addressService;
    }

    @GetMapping
    ModelAndView getAccountPage(@AuthenticationPrincipal UserDetails principal) {
        Address actualAddress = addressService.findByUserId(principal.getUsername())
                .orElse(Address.EMPTY_ADDRESS);
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("basketItemsCount", basket.countItems());
        pageData.put("address", actualAddress);
        pageData.put("email", principal.getUsername());
        return new ModelAndView("account", pageData);
    }

    @PostMapping("/update")
    RedirectView updateAddress(@Valid Address address, @AuthenticationPrincipal UserDetails principal) {
        addressService.save(address, principal.getUsername());
        return new RedirectView("/");
    }

}
