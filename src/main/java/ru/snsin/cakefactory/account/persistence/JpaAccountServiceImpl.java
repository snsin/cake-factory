package ru.snsin.cakefactory.account.persistence;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.account.AccountService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaAccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;

    public JpaAccountServiceImpl(AccountRepository accountRepository, PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public List<Account> findAll() {
        final List<AccountEntity> all = accountRepository.findAll();
        return all.stream()
                .map(this::mapEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        final Optional<AccountEntity> userEntity = accountRepository.findById(email);
        return userEntity.map(this::mapEntityToUser);
    }

    @Override
    public void save(Account account) {
        Objects.requireNonNull(account);
        final String email = account.getEmail();
        if (accountRepository.existsById(email)) {
            final String message = String.format("User with email = %s already exists", email);
            throw new RuntimeException(message);
        }
        accountRepository.save(mapUserToUserEntity(account));
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

    private AccountEntity mapUserToUserEntity(Account account) {
        final AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail(account.getEmail());
        accountEntity.setPassword(account.getPassword());
        return accountEntity;
    }
}
