package ru.snsin.cakefactory.account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    Optional<Account> findByEmail(String email);
    void save(Account account);
    void register(String email, String password);
    boolean exists(String email);
}