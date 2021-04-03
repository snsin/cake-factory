package ru.snsin.cakefactory.users;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.client.BrowserClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/insert-mock-user.sql")
@Sql(scripts = "/sql/delete-mock-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsersIntegrationTest {

    @Autowired
    private Environment testEnv;

    @Autowired
    private MockMvc mockMvc;


    private BrowserClient browser;
    private Faker faker;


    @BeforeEach
    void setUp() {
        browser = new BrowserClient(mockMvc, testEnv);
        faker = new Faker();
    }

    @Test
    void loginButtonShouldExist() throws IOException {
        browser.goToHomePage();
        final String loginButtonText = browser.getLoginOrAccountLinkText();
        assertEquals("Login", loginButtonText);
    }

    @Test
    void signupPageShouldExists() throws IOException {
        browser.goToSignupPage();
        final String signupPageText = browser.pageText();
        assertTrue(signupPageText.contains("Login"));
        assertTrue(signupPageText.contains("Email"));
    }

    @Test
    void shouldSignUpWhenClickSignupButton() throws IOException {
        browser.goToLoginPage();
        browser.clickToSignUpLink();
        final String userEmail = "login@example.com";
        browser.fillInUserCredentials(userEmail, "password");
        browser.fillInAddress("Line One st.", "Line 2", "PSCODE");
        browser.clickToSignUpButton();

        String actualMenuText = browser.getLoginOrAccountLinkText();
        assertEquals(userEmail, actualMenuText);
    }

    @Test
    void signupFormShouldContainsCsrfToken() throws IOException {
        browser.goToSignupPage();
        final String signupForm = browser.getCsrfInput();

        assertTrue(signupForm.contains("_csrf"));
    }

    @Test
    void addressShouldBePopulatedAfterSignup() throws IOException {
        Account account = new Account(faker.internet().safeEmailAddress(), faker.internet().password());
        Address address = makeAddress();

        browser.goToSignupPage();
        browser.fillInUserCredentials(account.getEmail(), account.getPassword());
        browser.fillInAddress(address.getAddressLine1(), address.getAddressLine2(), address.getPostcode());
        browser.clickToSignUpButton();
        browser.goToBasketPage();

        Address populatedAddress = browser.getAddress();
        assertEquals(address, populatedAddress);
    }

    @Test
    void loginPageShouldExist() throws IOException {
        browser.goToLoginPage();
        Account account = new Account(faker.internet().safeEmailAddress(), faker.internet().password());
        browser.fillInUserCredentials(account.getEmail(), account.getPassword());

        assertEquals(account.getEmail(), browser.getEmailInputText());
        assertEquals(account.getPassword(), browser.getPasswordInputText());
    }

    @Test
    void userShouldBeAbleToLoginIfStoredInDb() throws Exception {
        browser.goToLoginPage();
        browser.fillInUserCredentials("user@mail.ru", "password");
        browser.clickLoginButton();
        String actualMenuText = browser.getLoginOrAccountLinkText();
        assertEquals("user@mail.ru", actualMenuText);
    }

    private Address makeAddress() {
        return new Address(faker.address().streetAddress(),
                faker.address().secondaryAddress(), faker.address().zipCode());
    }
}
