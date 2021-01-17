package ru.snsin.cakefactory.services;

import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;

import java.util.List;

public interface BasketService {
    int countItems();
    void addItem(String sku);
    List<CakeItem> getItems();
    List<BasketItem> getBasketItems();
    void removeItem(String sku);
    void clearBasket();
}
