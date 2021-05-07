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

import javax.transaction.Transactional;

@Component
public class SignUpComponent implements SignUp {

    private final AccountService accountService;
    private final AddressService addressService;

    public SignUpComponent(AccountService accountService, AddressService addressService) {
        this.accountService = accountService;
        this.addressService = addressService;
    }

    @Override
    public boolean accountExists(String email) {
        return accountService.exists(email);
    }

    @Override
    @Transactional
    public void signUp(Account account, Address address) {
        accountService.register(account.getEmail(), account.getPassword());
        addressService.save(address, account.getEmail());
    }
}
