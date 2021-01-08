package ru.snsin.cakefactory.services;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;

@Service
public class BasketServiceImpl implements BasketService {

    private int items = 0;

    @Override
    public int countItems() {
        return items;
    }

    @Override
    public void addItem(CakeItem cakeItem) {
        items += 1;

    }
}
