package ru.snsin.cakefactory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketServiceImplTest {

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketService = new BasketServiceImpl();
    }

    @Test
    void shouldIncreaseItemsCountOnAdd() {
        final String cakeName = "All Butter Croissant";
        basketService.addItem(cakeName);
        assertEquals(1, basketService.countItems());
        basketService.addItem(cakeName);
        assertEquals(2, basketService.countItems());
    }
}