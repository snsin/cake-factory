package ru.snsin.cakefactory.services;

import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;

import java.util.List;

public interface BasketService {
    int countItems();
    void addItem(CakeItem cake);
    List<CakeItem> getItems();
    List<BasketItem> getNameCountPairs();
    void removeItem(String cakeName);
}
