package ru.snsin.cakefactory.users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.account.AccountService;

import java.util.Objects;

@Component
public class SignUpComponent implements SignUp {

    private final AccountService accountService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public SignUpComponent(AccountService accountService, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(Account account, Address address) {
        final Account actualAccount = makeAccountWithEncodedPassword(account);
        accountService.save(actualAccount);
        addressService.save(address, actualAccount.getEmail());
        setSecurityContext(actualAccount);
    }

    private Account makeAccountWithEncodedPassword(Account account) {
        String plainPassword = Objects.requireNonNull(account.getPassword());
        return new Account(account.getEmail(), passwordEncoder.encode(plainPassword));
    }

    private void setSecurityContext(Account account) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails principal = User.withUsername(account.getEmail())
                .password(account.getPassword()).roles(Account.ROLE_NAME).build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }
}
