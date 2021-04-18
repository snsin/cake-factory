package ru.snsin.cakefactory.users;

import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.address.Address;

public interface SignUp {
    boolean accountExists(String email);
    void signUp(Account account, Address address);
}
