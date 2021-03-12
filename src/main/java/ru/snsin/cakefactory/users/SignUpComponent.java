package ru.snsin.cakefactory.users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
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
        setSecurityContext(actualAccount.getEmail());
    }

    private Account makeAccountWithEncodedPassword(Account account) {
        String plainPassword = Objects.requireNonNull(account.getPassword());
        return new Account(account.getEmail(), passwordEncoder.encode(plainPassword));
    }

    private void setSecurityContext(String email) {
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(Account.ACCOUNT_AUTHORITY);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(email, null, authorities);

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }
}
