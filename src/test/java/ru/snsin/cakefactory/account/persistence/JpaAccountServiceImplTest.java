package ru.snsin.cakefactory.account.persistence;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.snsin.cakefactory.account.Account;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaAccountServiceImplTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    AccountRepository accountRepository;

    private JpaAccountServiceImpl jpaAccountService;
    private Faker faker;

    @BeforeEach
    void setUp() {
        jpaAccountService = new JpaAccountServiceImpl(accountRepository);
        faker = new Faker();
    }

    @Test
    void shouldFindAllEntities() {
        AccountEntity user1 = createUser("user1@mail.com");
        AccountEntity user2 = createUser("user2@mail.com");

        final List<Account> allAccounts = jpaAccountService.findAll();

        assertEquals(2, allAccounts.size());
        assertTrue(allAccounts.stream().anyMatch(user -> user.getEmail().equals(user1.getEmail())));
        assertTrue(allAccounts.stream().anyMatch(user -> user.getEmail().equals(user2.getEmail())));
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryIsEmpty() {
        testEntityManager.getEntityManager()
                .createQuery("DELETE FROM AccountEntity").executeUpdate();
        assertTrue(jpaAccountService.findAll().isEmpty());
    }

    @Test
    void shouldFindUserByEmail() {

        AccountEntity existingUser = createUser("existing@example.com");

        final Optional<Account> foundByEmail = jpaAccountService.findByEmail(existingUser.getEmail());
        final Optional<Account> notExisting = jpaAccountService.findByEmail("lost@email.com");

        assertTrue(foundByEmail.isPresent());
        assertTrue(notExisting.isEmpty());
    }

    @Test
    void shouldSaveUsers() {
        Account account1 = new Account("user@mail.com", "abc");
        Account account2 = new Account("user@gmail.com", "123");

        jpaAccountService.save(account1);
        jpaAccountService.save(account2);

        assertEquals(2, jpaAccountService.findAll().size());
    }

    @Test
    void shouldNotSaveUserIfUserIfEmailAlreadyExists() {
        AccountEntity user = createUser("user@mail.com");
        Account accountWithSameEmail = new Account(user.getEmail(), faker.internet().password());

        assertThrows(RuntimeException.class, () -> jpaAccountService.save(accountWithSameEmail));

    }

    private AccountEntity createUser(String email) {
        AccountEntity user = new AccountEntity();
        String password = faker.internet().password();
        user.setEmail(email);
        user.setPassword(password);

        testEntityManager.persistAndFlush(user);
        return user;
    }
}
