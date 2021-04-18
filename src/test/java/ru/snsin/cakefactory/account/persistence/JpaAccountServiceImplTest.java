package ru.snsin.cakefactory.account.persistence;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        jpaAccountService = new JpaAccountServiceImpl(accountRepository, new BCryptPasswordEncoder());
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
    void shouldRegisterUserAndEncryptPassword() {
        String email = "user@mail.com";
        String password = faker.internet().password();
        jpaAccountService.register(email, password);

        AccountEntity registeredUser = testEntityManager.find(AccountEntity.class, email);

        assertEquals(email, registeredUser.getEmail());
        assertNotEquals(password, registeredUser.getPassword());
    }

    @Test
    void shouldNotRegisterUserIfUserEmailAlreadyExists() {
        AccountEntity user = createUser("user@mail.com");

        assertThrows(RuntimeException.class, () -> jpaAccountService.register(user.getEmail(), "pass"));

    }

    @Test
    void shouldChecksForExistence() {
        AccountEntity existingUser = createUser("existing@example.com");

        assertTrue(jpaAccountService.exists(existingUser.getEmail()));
        assertFalse(jpaAccountService.exists("lost@email.com"));
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
