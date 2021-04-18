package ru.snsin.cakefactory.account.persistence;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.account.AccountService;

import java.util.Objects;
import java.util.Optional;

@Service
public class JpaAccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;

    public JpaAccountServiceImpl(AccountRepository accountRepository, PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        final Optional<AccountEntity> userEntity = accountRepository.findById(email);
        return userEntity.map(this::mapEntityToUser);
    }


    @Override
    public void register(String email, String password) {
        if (accountRepository.existsById(email)) {
            final String message = String.format("User with email = %s already exists", email);
            throw new RuntimeException(message);
        }
        final AccountEntity accountCandidate = new AccountEntity();
        accountCandidate.setEmail(email);
        accountCandidate.setPassword(encoder.encode(Objects.requireNonNull(password)));
        accountRepository.save(accountCandidate);
    }

    @Override
    public boolean exists(String email) {
        return accountRepository.existsById(email);
    }

    private Account mapEntityToUser(AccountEntity accountEntity) {
        return new Account(accountEntity.getEmail(), accountEntity.getPassword());
    }
}
