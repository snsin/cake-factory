package ru.snsin.cakefactory.users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.account.AccountService;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.address.AddressService;

@Component
public class SignUpComponent implements SignUp {

    private final AccountService accountService;
    private final AddressService addressService;

    public SignUpComponent(AccountService accountService, AddressService addressService) {
        this.accountService = accountService;
        this.addressService = addressService;
    }

    @Override
    public void signUp(Account account, Address address) {
        accountService.register(account.getEmail(), account.getPassword());
        addressService.save(address, account.getEmail());
        setSecurityContext(account);
    }

    private void setSecurityContext(Account account) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails principal = User.withUsername(account.getEmail())
                .password("").roles(Account.ROLE_NAME).build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }
}
