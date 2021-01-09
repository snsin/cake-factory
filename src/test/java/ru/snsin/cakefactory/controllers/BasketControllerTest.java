package ru.snsin.cakefactory.controllers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.services.BasketService;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
class BasketControllerTest {

    @Autowired
    WebClient webClient;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CakeCatalogService cakeCatalog;

    @MockBean
    BasketService basketService;


    @Test
    void addItem() throws Exception {
        final String expectedItemName = "Victoria Sponge";

        mockMvc.perform(post("/basket")
                .queryParam("cakeName", expectedItemName))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldRenderBasketNavItem() throws IOException {
        String expectedBasketNavContent = "Basket: 1 item(s)";

        Mockito.when(basketService.countItems()).thenReturn(1);
        final HtmlPage page = webClient.getPage("/basket");
        final DomNodeList<DomNode> navigations =
                page.querySelectorAll("li.nav-item > .nav-link");

        final Optional<DomNode> domNodeOptional = navigations.stream()
                .filter(link -> link.asText().contains(expectedBasketNavContent))
                .findAny();
        assertEquals(HtmlParagraph.class, domNodeOptional.orElseThrow().getClass());
    }

    @Test
    void shouldRenderBasketItems() throws IOException {
        final String chocolateCroissant = "Chocolate Croissant";
        final BasketItem expectedItem = new BasketItem(chocolateCroissant, 1);

        Mockito.when(basketService.getNameCountPairs())
                .thenReturn(Collections.singletonList(expectedItem));

        final HtmlPage page = webClient.getPage("/basket");
        final HtmlTableRow croissantRow = page.querySelector("tbody tr");
        assertTrue(croissantRow.asText().contains(chocolateCroissant));
        assertTrue(croissantRow.asText().contains("x1"));

    }

    @Test
    void shouldRenderRemoveButton() throws IOException {
        final String baguette = "Fresh Baguette";
        final BasketItem expectedItem = new BasketItem(baguette, 2);

        Mockito.when(basketService.getNameCountPairs())
                .thenReturn(Collections.singletonList(expectedItem));

        final HtmlPage page = webClient.getPage("/basket");
        final HtmlSubmitInput removeButton =
                page.querySelector("tbody tr form input[type=submit]");
        assertTrue(removeButton.asText().contains("Remove"));
        assertDoesNotThrow(() -> removeButton.click());
    }
}