package ru.snsin.cakefactory.users;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    Optional<Account> findByEmail(String email);
    void save(Account account);
}
