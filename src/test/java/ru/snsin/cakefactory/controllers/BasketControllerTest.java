package ru.snsin.cakefactory.controllers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.components.Basket;
import ru.snsin.cakefactory.services.CakeCatalogService;
import ru.snsin.cakefactory.users.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    @Autowired
    WebClient webClient;

    @Autowired
    AccountService accountService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CakeCatalogService cakeCatalog;

    @MockBean
    Basket basketService;


    @Test
    void addItem() throws Exception {
        final String itemSku = "vs";

        mockMvc.perform(post("/basket").with(csrf())
                .queryParam("sku", itemSku))
                .andExpect(status().is3xxRedirection());

        Mockito.verify(basketService).addItem(itemSku);
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
        final BasketItem expectedItem = createBasketItem(chocolateCroissant, 1);

        Mockito.when(basketService.getBasketItems())
                .thenReturn(Collections.singletonList(expectedItem));

        final HtmlPage page = webClient.getPage("/basket");
        final HtmlTableRow croissantRow = page.querySelector("tbody tr");
        assertTrue(croissantRow.asText().contains(chocolateCroissant));
        assertTrue(croissantRow.asText().contains("x1"));

    }

    @Test
    void shouldRenderRemoveButton() throws IOException {
        final String baguette = "Fresh Baguette";
        final BasketItem expectedItem = createBasketItem(baguette, 2);

        Mockito.when(basketService.getBasketItems())
                .thenReturn(Collections.singletonList(expectedItem));

        final HtmlPage page = webClient.getPage("/basket");
        final HtmlSubmitInput removeButton =
                page.querySelector("tbody tr form input[type=submit]");
        assertTrue(removeButton.asText().contains("Remove"));
        assertDoesNotThrow((ThrowingSupplier<Object>) removeButton::click);
    }

    @Test
    void shouldRenderCompleteOrderForm() throws IOException {
        final String carrotCakeName = "Carrot Cake";

        Mockito.when(basketService.getBasketItems())
                .thenReturn(Collections.singletonList(createBasketItem(carrotCakeName, 1)));

        final HtmlPage basketPage = webClient.getPage("/basket");
        final HtmlForm orderInfo =
                assertDoesNotThrow(() -> basketPage.getFormByName("orderInfo"));
        final HtmlButton completeOrderButton = orderInfo.querySelector("button[type=submit]");
        assertTrue(completeOrderButton.asText().contains("Complete order"));
    }

    @Test
    void shouldRemoveItemFromBasket() throws Exception {
        final String itemSku = "rv";

        mockMvc.perform(post("/basket/delete").with(csrf())
                .queryParam("sku", itemSku))
                .andExpect(status().is3xxRedirection());

        Mockito.verify(basketService).removeItem(itemSku);
    }

    private BasketItem createBasketItem(String name, int count) {
        CakeItem cake = new CakeItem(name, name, BigDecimal.TEN);
        return new BasketItem(cake, count);
    }
}