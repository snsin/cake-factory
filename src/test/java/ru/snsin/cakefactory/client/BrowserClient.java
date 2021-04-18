package ru.snsin.cakefactory.client;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import ru.snsin.cakefactory.address.Address;

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

    public void clickToSignUpLink() throws IOException {
        HtmlAnchor signupLink = this.currentPage.querySelector("#signup-link");
        this.currentPage = signupLink.click();
    }

    public String getCsrfInput() {
        return this.currentPage.querySelector("input[name='_csrf']").toString();
    }

    public void goToBasketPage() throws IOException {
        this.currentPage = this.mvcInitClient.getPage("/basket");
    }

    public Address getAddress() {
        String addressLine1 = getInputValue("#addressLine1");
        String addressLine2 = getInputValue("#addressLine2");
        String postcode = getInputValue("#postCode");
        return new Address(addressLine1, addressLine2, postcode);
    }

    private void setValue(String selector, String value) {
        final HtmlInput input = this.currentPage.querySelector(selector);
        input.setValueAttribute(value);
    }

    private String getInputValue(String selector) {
        final HtmlInput input = this.currentPage.querySelector(selector);
        return input.getValueAttribute();
    }

    public void goToLoginPage() throws IOException {
        this.currentPage = mvcInitClient.getPage("/login");
    }

    public String getLoginOrAccountLinkText() {
        return currentPage.getElementById("login-or-account").asText();
    }

    public void clickLoginButton() throws IOException {
        HtmlElement loginButton = currentPage.querySelector("#login-button");
        this.currentPage = loginButton.click();
    }

    public String getEmailInputText() {
        return getInputValue("#user-email");
    }

    public String getPasswordInputText() {
        return getInputValue("#user-password");
    }

    public void goToAccountPage() throws IOException {
        this.currentPage = mvcInitClient.getPage("/account");
    }

    public void clickToUpdateButton() throws IOException {
        HtmlElement updateButton = currentPage.querySelector("#update-button");
        this.currentPage = updateButton.click();
    }
}
