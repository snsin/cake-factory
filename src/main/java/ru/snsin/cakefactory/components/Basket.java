package ru.snsin.cakefactory.components;

import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;

import java.util.List;

public interface Basket {
    int countItems();
    void addItem(String sku);
    List<CakeItem> getItems();
    List<BasketItem> getBasketItems();
    void removeItem(String sku);
    void clearBasket();
}
