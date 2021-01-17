package ru.snsin.cakefactory.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionBasketTest {

    CakeCatalogService cakeCatalogService;

    private Basket basket;
    private String baguetteSku;
    private String croissantSku;

    @BeforeEach
    void setUp() {
        cakeCatalogService = Mockito.mock(CakeCatalogService.class);
        basket = new SessionBasket(cakeCatalogService);
        baguetteSku = "b";
        croissantSku = "abcr";
        Mockito.when(cakeCatalogService.getItemBySku(croissantSku))
                .thenReturn(new CakeItem(croissantSku, "All Butter Croissant", new BigDecimal("0.95")));
        Mockito.when(cakeCatalogService.getItemBySku(baguetteSku))
                .thenReturn(new CakeItem(baguetteSku, "Fresh Baguette", new BigDecimal("0.75")));
    }

    @Test
    void shouldIncreaseItemsCountOnAdd() {
        basket.addItem(croissantSku);
        assertEquals(1, basket.countItems());
        basket.addItem(croissantSku);
        assertEquals(2, basket.countItems());
    }

    @Test
    void shouldReturnCountedItems() {

        basket.addItem(croissantSku);
        basket.addItem(croissantSku);
        basket.addItem(baguetteSku);

        assertEquals(2, getItemCount(croissantSku).orElseThrow());
        assertEquals(1, getItemCount(baguetteSku).orElseThrow());
    }

    @Test
    void shouldRemoveItems() {

        basket.addItem(baguetteSku);
        basket.addItem(baguetteSku);

        basket.removeItem(baguetteSku);
        assertEquals(1, getItemCount(baguetteSku).orElseThrow());

        basket.removeItem(baguetteSku);
        assertTrue(getItemCount(baguetteSku).isEmpty());
    }

    private Optional<Integer> getItemCount(String itemSku) {
        return basket.getBasketItems().stream()
                .filter(item -> item.getCake().getSku().equals(itemSku))
                .findAny().map(BasketItem::getCount);
    }
}