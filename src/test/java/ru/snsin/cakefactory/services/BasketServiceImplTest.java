package ru.snsin.cakefactory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.snsin.cakefactory.components.BasketServiceImpl;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasketServiceImplTest {

    CakeCatalogService cakeCatalogService;

    private BasketService basketService;
    private String baguetteSku;
    private String croissantSku;

    @BeforeEach
    void setUp() {
        cakeCatalogService = Mockito.mock(CakeCatalogService.class);
        basketService = new BasketServiceImpl(cakeCatalogService);
        baguetteSku = "b";
        croissantSku = "abcr";
        Mockito.when(cakeCatalogService.getItemBySku(croissantSku))
                .thenReturn(new CakeItem(croissantSku, "All Butter Croissant", new BigDecimal("0.95")));
        Mockito.when(cakeCatalogService.getItemBySku(baguetteSku))
                .thenReturn(new CakeItem(baguetteSku, "Fresh Baguette", new BigDecimal("0.75")));
    }

    @Test
    void shouldIncreaseItemsCountOnAdd() {
        basketService.addItem(croissantSku);
        assertEquals(1, basketService.countItems());
        basketService.addItem(croissantSku);
        assertEquals(2, basketService.countItems());
    }

    @Test
    void shouldReturnCountedItems() {

        basketService.addItem(croissantSku);
        basketService.addItem(croissantSku);
        basketService.addItem(baguetteSku);

        assertEquals(2, getItemCount(croissantSku).orElseThrow());
        assertEquals(1, getItemCount(baguetteSku).orElseThrow());
    }

    @Test
    void shouldRemoveItems() {

        basketService.addItem(baguetteSku);
        basketService.addItem(baguetteSku);

        basketService.removeItem(baguetteSku);
        assertEquals(1, getItemCount(baguetteSku).orElseThrow());

        basketService.removeItem(baguetteSku);
        assertTrue(getItemCount(baguetteSku).isEmpty());
    }

    private Optional<Integer> getItemCount(String itemSku) {
        return basketService.getBasketItems().stream()
                .filter(item -> item.getCake().getSku().equals(itemSku))
                .findAny().map(BasketItem::getCount);
    }
}