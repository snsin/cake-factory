package ru.snsin.cakefactory.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.client.BrowserClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersIntegrationTest {

    @Autowired
    private Environment testEnv;

    @Autowired
    private MockMvc mockMvc;


    private BrowserClient browser;


    @BeforeEach
    void setUp() {
        browser = new BrowserClient(mockMvc, testEnv);
    }

    @Test
    void loginButtonShouldExist() throws IOException {
        browser.goToHomePage();
        final String homePageText = browser.pageText();
        assertTrue(homePageText.contains("Login"));
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
        browser.goToSignupPage();
        final String userEmail = "login@example.com";
        browser.fillInUserCredentials(userEmail, "password");
        browser.fillInAddress("Line One st.", "Line 2", "PSCODE");
        browser.clickToSignUpButton();

        assertTrue(browser.pageText().contains(userEmail));
    }

    @Test
    void signupFormShouldContainsCsrfToken() throws IOException {
        browser.goToSignupPage();
        final String signupForm = browser.getCsrfInput();

        assertTrue(signupForm.contains("_csrf"));
    }
}
