package ru.snsin.cakefactory.users;

public interface SignUp {
    String getEmail();
    Address getAddress();
    void signUp(Account account, Address address);
}
