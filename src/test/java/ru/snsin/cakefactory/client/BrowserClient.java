package ru.snsin.cakefactory.client;

import com.gargoylesoftware.htmlunit.WebClient;
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

    public String pageText() {
        return this.currentPage.asText();
    }
}
