package ru.snsin.cakefactory.services;

import ru.snsin.cakefactory.domain.CakeItem;

public interface BasketService {
    int countItems();
    void addItem(CakeItem cakeItem);
}
