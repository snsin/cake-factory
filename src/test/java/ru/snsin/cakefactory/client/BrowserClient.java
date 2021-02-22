package ru.snsin.cakefactory.client;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.io.IOException;

@Slf4j
public class BrowserClient {

    private final WebClient mvcInitClient;
    private HtmlPage currentPage;

    public BrowserClient(MockMvc mockMvc, Environment testEnv) {
        mvcInitClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .withDelegate(new LocalHostWebClient(testEnv)).build();

    }

    public void goToHomePage() throws IOException {
        this.currentPage = this.mvcInitClient.getPage("/");
    }

    public void goToSignupPage() throws IOException {
        this.currentPage = this.mvcInitClient.getPage("/signup");
    }

    public String pageText() {
        return this.currentPage.asText();
    }

    public void fillInUserCredentials(String login, String password) {
        setValue("#user-email", login);
        setValue("#user-password", password);
    }

    public void fillInAddress(String line1, String line2, String postcode) {
        setValue("#addressLine1", line1);
        setValue("#addressLine2", line2);
        setValue("#postCode", postcode);
    }

    public void clickToSignUpButton() throws IOException {
        final HtmlElement signUpButton = this.currentPage.querySelector("#sign-up");
        this.currentPage = signUpButton.click();

    }

    private void setValue(String selector, String value) {
        final HtmlInput input = this.currentPage.querySelector(selector);
        input.setValueAttribute(value);
    }
}
