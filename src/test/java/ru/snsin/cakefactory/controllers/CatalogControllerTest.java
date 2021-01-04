package ru.snsin.cakefactory.controllers;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    WebClient mvc;

    @Test
    void shouldReturnIndex() throws Exception {
        final Page page = mvc.getPage("/");
        assertTrue(page.isHtmlPage());
    }
}