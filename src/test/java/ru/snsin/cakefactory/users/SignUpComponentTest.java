package ru.snsin.cakefactory.users;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpComponentTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AddressService addressService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private SignUp signUp;
    private Faker faker;

    @BeforeEach
    void setUp() {
        signUp = new SignUpComponent(accountService, addressService, passwordEncoder);
        faker = new Faker();
    }

    @Test
    void shouldEncodePasswordWhenSignUp() {
        Account account = new Account("user@example.com", faker.internet().password());

        signUp.signUp(account, makeAddress());

        Account savedAccount = accountService.findByEmail(account.getEmail()).orElseThrow();
        assertNotEquals(account.getPassword(), savedAccount.getPassword());
        assertTrue(passwordEncoder.matches(account.getPassword(), savedAccount.getPassword()));
    }

    @Test
    void shouldSaveAccountAndAddressAfterSignUp() {
        Account account = new Account("user1@example.com", faker.internet().password());
        Address address = makeAddress();

        signUp.signUp(account, address);

        assertTrue(accountService.findByEmail(account.getEmail()).isPresent());
        assertEquals(address, addressService.findByUserId(account.getEmail()).orElseThrow());
    }

    @Test
    void getAddress() {
        Address address = makeAddress();
        Account account = new Account("test@example.com", faker.internet().password());

        signUp.signUp(account, address);

        assertEquals(address, addressService.findByUserId(account.getEmail()).orElseThrow());

    }

    private Address makeAddress() {
        return new Address(faker.address().streetAddress(),
                faker.address().secondaryAddress(), faker.address().zipCode());
    }
}