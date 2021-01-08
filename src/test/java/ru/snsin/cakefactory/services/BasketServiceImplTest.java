package ru.snsin.cakefactory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.storage.BasketServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketServiceImplTest {

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketService = new BasketServiceImpl();
    }

    @Test
    void shouldIncreaseItemsCountOnAdd() {
        final CakeItem cake = new CakeItem("All Butter Croissant", new BigDecimal("0.95"));
        basketService.addItem(cake);
        assertEquals(1, basketService.countItems());
        basketService.addItem(cake);
        assertEquals(2, basketService.countItems());
    }

    @Test
    void shouldReturnCountedItems() {
        final String croissantName = "All Butter Croissant";
        final String baguetteName = "Fresh Baguette";
        final CakeItem croissant = new CakeItem(croissantName, new BigDecimal("0.95"));

        basketService.addItem(croissant);
        basketService.addItem(croissant);
        basketService.addItem(new CakeItem(baguetteName, new BigDecimal("0.75")));

        assertEquals(2, getItemCount(croissantName).orElseThrow());
        assertEquals(1, getItemCount(baguetteName).orElseThrow());
    }

    private Optional<Integer> getItemCount(String itemName) {
        return basketService.getNameCountPairs().stream()
                .filter(item -> item.getName().equals(itemName))
                .findAny().map(BasketItem::getCount);
    }
}