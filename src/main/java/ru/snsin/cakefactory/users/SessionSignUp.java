package ru.snsin.cakefactory.users;

import lombok.Getter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class SessionSignUp implements SignUp {

    private final AccountService accountService;
    private final AddressService addressService;

    @Getter
    private String email;
    @Getter
    private Address address;

    public SessionSignUp(AccountService accountService, AddressService addressService) {
        this.accountService = accountService;
        this.addressService = addressService;
    }

    @Override
    public void signUp(Account account, Address address) {
        accountService.save(account);
        this.email = account.getEmail();
        addressService.save(address, account.getEmail());
        this.address = address;

    }
}
