package ru.snsin.cakefactory.services;

import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

    private int items = 0;

    @Override
    public int countItems() {
        return items;
    }

    @Override
    public void addItem(String cakeName) {
        items += 1;

    }
}
