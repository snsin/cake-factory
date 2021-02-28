package ru.snsin.cakefactory.users.persistence;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.snsin.cakefactory.users.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaUserServiceImplTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    private JpaUserServiceImpl jpaUserService;
    private Faker faker;

    @BeforeEach
    void setUp() {
        jpaUserService = new JpaUserServiceImpl(userRepository);
        faker = new Faker();
    }

    @Test
    void shouldFindAllEntities() {
        UserEntity user1 = createUser("user1@mail.com");
        UserEntity user2 = createUser("user2@mail.com");

        final List<User> allUsers = jpaUserService.findAll();

        assertEquals(2, allUsers.size());
        assertTrue(allUsers.stream().anyMatch(user -> user.getEmail().equals(user1.getEmail())));
        assertTrue(allUsers.stream().anyMatch(user -> user.getEmail().equals(user2.getEmail())));
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryIsEmpty() {
        testEntityManager.getEntityManager()
                .createQuery("DELETE FROM UserEntity").executeUpdate();
        assertTrue(jpaUserService.findAll().isEmpty());
    }

    @Test
    void shouldFindUserByEmail() {

        UserEntity existingUser = createUser("existing@example.com");

        final Optional<User> foundByEmail = jpaUserService.findByEmail(existingUser.getEmail());
        final Optional<User> notExisting = jpaUserService.findByEmail("lost@email.com");

        assertTrue(foundByEmail.isPresent());
        assertTrue(notExisting.isEmpty());
    }

    @Test
    void shouldSaveUsers() {
        User user1 = new User("user@mail.com", "abc");
        User user2 = new User("user@gmail.com", "123");

        jpaUserService.save(user1);
        jpaUserService.save(user2);

        assertEquals(2, jpaUserService.findAll().size());
    }

    @Test
    void shouldNotSaveUserIfUserIfEmailAlreadyExists() {
        UserEntity user = createUser("user@mail.com");
        User userWithSameEmail = new User(user.getEmail(), faker.internet().password());

        assertThrows(RuntimeException.class, () -> jpaUserService.save(userWithSameEmail));

    }

    private UserEntity createUser(String email) {
        UserEntity user = new UserEntity();
        String password = faker.internet().password();
        user.setEmail(email);
        user.setPassword(password);

        testEntityManager.persistAndFlush(user);
        return user;
    }
}
