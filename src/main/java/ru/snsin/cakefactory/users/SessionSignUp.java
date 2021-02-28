package ru.snsin.cakefactory.users;

import lombok.Getter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class SessionSignUp implements SignUp {

    private final UserService userService;
    private final AddressService addressService;

    @Getter
    private String email;
    @Getter
    private Address address;

    public SessionSignUp(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    public void signUp(User user, Address address) {
        userService.save(user);
        this.email = user.getEmail();
        addressService.save(address, user.getEmail());
        this.address = address;

    }
}
