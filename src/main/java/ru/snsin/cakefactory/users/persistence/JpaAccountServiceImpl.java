package ru.snsin.cakefactory.users.persistence;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.users.Account;
import ru.snsin.cakefactory.users.AccountService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaAccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public JpaAccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
