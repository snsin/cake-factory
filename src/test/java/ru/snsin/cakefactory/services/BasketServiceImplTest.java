package ru.snsin.cakefactory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.snsin.cakefactory.domain.CakeItem;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceImplTest {

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketService = new BasketServiceImpl();
    }

    @Test
    void shouldIncreaseItemsCountOnAdd() {
        final CakeItem addingItem = new CakeItem("All Butter Croissant", new BigDecimal("0.75"));
        basketService.addItem(addingItem);
        assertEquals(1, basketService.countItems());
        basketService.addItem(addingItem);
        assertEquals(2, basketService.countItems());
    }
}