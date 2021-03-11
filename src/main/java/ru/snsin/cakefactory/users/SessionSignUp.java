package ru.snsin.cakefactory.users;

import lombok.Getter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Objects;

@Component
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class SessionSignUp implements SignUp {

    private final AccountService accountService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Getter
    private String email;
    @Getter
    private Address address;

    public SessionSignUp(AccountService accountService, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(Account account, Address address) {
        final Account actualAccount = makeAccountWithEncodedPassword(account);
        accountService.save(actualAccount);
        this.email = actualAccount.getEmail();
        addressService.save(address, actualAccount.getEmail());
        this.address = address;

    }

    private Account makeAccountWithEncodedPassword(Account account) {
        String plainPassword = Objects.requireNonNull(account.getPassword());
        return new Account(account.getEmail(), passwordEncoder.encode(plainPassword));
    }
}
