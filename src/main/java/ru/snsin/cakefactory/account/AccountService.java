package ru.snsin.cakefactory.account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByEmail(String email);
    void register(String email, String password);
    boolean exists(String email);
}
