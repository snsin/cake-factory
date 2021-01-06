package ru.snsin.cakefactory.controllers;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    WebClient mvc;

    @MockBean
    CakeCatalogService cakeCatalog;

    @Test
    void shouldReturnIndex() throws Exception {
        final Page page = mvc.getPage("/");
        assertTrue(page.isHtmlPage());
    }

    @Test
    void shouldRenderItems() throws Exception {

        final List<CakeItem> items = new ArrayList<>();
        items.add(new CakeItem("Red Velvet", new BigDecimal("3.95")));
        items.add(new CakeItem("Fresh Baguette", new BigDecimal("1.60")));
        items.add(new CakeItem("Victoria Sponge", new BigDecimal("5.45")));
        final List<String> itemsNames = items.stream()
                .map(CakeItem::getName).collect(Collectors.toList());

        Mockito.when(cakeCatalog.getAll()).thenReturn(items);
        final HtmlPage page = mvc.getPage("/");
        final List<String> titles = page.querySelectorAll("div.card").stream()
                .map(card -> card.querySelector("h4.card-title").getVisibleText())
                .collect(Collectors.toList());

        assertEquals(items.size(), titles.size());
        assertTrue(titles.containsAll(itemsNames));
    }



    @Test
    void shouldDisplayPriceInPounds() throws IOException {
        List<CakeItem> redVelvetList =
                Collections.singletonList(new CakeItem("Red Velvet", new BigDecimal("3.90")));

        Mockito.when(cakeCatalog.getAll()).thenReturn(redVelvetList);
        final HtmlPage page = mvc.getPage("/");
        final DomNode redVelvetCard = page.querySelector("div.card");
        final String redVelvetTitle = redVelvetCard.querySelector("h4.card-title").getVisibleText();
        final String redVelvetPrice = redVelvetCard.querySelector("h5").getVisibleText();

        assertEquals("Red Velvet", redVelvetTitle);
        assertEquals("£3.90", redVelvetPrice);
    }

    @Test
    void shouldDisplayBasketLink() throws IOException {
        Mockito.when(cakeCatalog.getAll()).thenReturn(Collections.emptyList());
        final HtmlPage page = mvc.getPage("/");
        final DomNodeList<DomNode> navigations =
                page.querySelectorAll("li.nav-item > a.nav-link");
        assertTrue(navigations.stream().anyMatch(link -> link.asText().contains("Basket")));
    }
}