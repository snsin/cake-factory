package ru.snsin.cakefactory.services;

import ru.snsin.cakefactory.domain.CakeItem;

import java.util.List;

public interface BasketService {
    int countItems();
    void addItem(CakeItem cakeItem);
    List<CakeItem> getItems();
}
