package ru.snsin.cakefactory.controllers;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.BasketService;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    WebClient webClient;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CakeCatalogService cakeCatalog;

    @MockBean
    BasketService basketService;

    @Test
    void shouldReturnIndex() throws Exception {
        final Page page = webClient.getPage("/");
        assertTrue(page.isHtmlPage());
    }

    @Test
    void shouldRenderItems() throws Exception {

        final List<CakeItem> items = new ArrayList<>();
        items.add(new CakeItem("rv","Red Velvet", new BigDecimal("3.95")));
        items.add(new CakeItem("b", "Fresh Baguette", new BigDecimal("1.60")));
        items.add(new CakeItem("vs", "Victoria Sponge", new BigDecimal("5.45")));
        final List<String> itemsNames = items.stream()
                .map(CakeItem::getName).collect(Collectors.toList());

        Mockito.when(cakeCatalog.getAll()).thenReturn(items);
        final HtmlPage page = webClient.getPage("/");
        final List<String> titles = page.querySelectorAll("div.card").stream()
                .map(card -> card.querySelector("h4.card-title").getVisibleText())
                .collect(Collectors.toList());

        assertEquals(items.size(), titles.size());
        assertTrue(titles.containsAll(itemsNames));
    }



    @Test
    void shouldDisplayPriceInPounds() throws IOException {
        List<CakeItem> redVelvetList =
                Collections.singletonList(new CakeItem("rv","Red Velvet", new BigDecimal("3.90")));

        Mockito.when(cakeCatalog.getAll()).thenReturn(redVelvetList);
        final HtmlPage page = webClient.getPage("/");
        final DomNode redVelvetCard = page.querySelector("div.card");
        final String redVelvetTitle = redVelvetCard.querySelector("h4.card-title").getVisibleText();
        final String redVelvetPrice = redVelvetCard.querySelector("h5").getVisibleText();

        assertEquals("Red Velvet", redVelvetTitle);
        assertEquals("£3.90", redVelvetPrice);
    }

    @Test
    void shouldDisplayEmptyBasket() throws IOException {
        final HtmlPage page = webClient.getPage("/");
        final DomNodeList<DomNode> navigations =
                page.querySelectorAll("li.nav-item > a.nav-link");
        assertTrue(navigations.stream().anyMatch(link -> link.asText().contains("Basket: 0 item(s)")));
    }

    @Test
    void shouldDisplayItemsInBasket() throws IOException {
        int expectedItemsCount = 2;
        String expectedBasketContent = String.format("Basket: %d item(s)", expectedItemsCount);
        Mockito.when(basketService.countItems()).thenReturn(expectedItemsCount);
        final HtmlPage page = webClient.getPage("/");
        final DomNodeList<DomNode> navigations =
                page.querySelectorAll("li.nav-item > a.nav-link");

        final Optional<DomNode> domNodeOptional = navigations.stream()
                .filter(link -> link.asText().contains(expectedBasketContent))
                .findAny();
        assertTrue(domNodeOptional.isPresent());
        HtmlAnchor basketLink = (HtmlAnchor) domNodeOptional.get();
        assertEquals("/basket", basketLink.getHrefAttribute());
    }

    @Test
    void shouldDisplayAddButtonOnItem() throws IOException {
        CakeItem expectedItem = new CakeItem("vs","Victoria Sponge", new BigDecimal("5.45"));

        Mockito.when(cakeCatalog.getAll()).thenReturn(Collections.singletonList(expectedItem));

        final HtmlPage page = webClient.getPage("/");
        final HtmlSubmitInput addButton = page.querySelector("div.card form input[type=submit]");
        assertTrue(addButton.asText().contains("Add"));

    }
}