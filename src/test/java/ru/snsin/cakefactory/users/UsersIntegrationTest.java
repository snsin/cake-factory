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
}
