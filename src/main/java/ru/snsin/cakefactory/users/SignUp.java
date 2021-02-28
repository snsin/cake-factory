package ru.snsin.cakefactory.users;

public interface SignUp {
    String getEmail();
    Address getAddress();
    void signUp(User user, Address address);
}
