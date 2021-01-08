package ru.snsin.cakefactory.services;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class BasketServiceImpl implements BasketService {

    private final List<CakeItem> items = new ArrayList<>();

    @Override
    public int countItems() {
        return items.size();
    }

    @Override
    public void addItem(CakeItem cakeItem) {
        items.add(Objects.requireNonNull(cakeItem));

    }

    @Override
    public List<CakeItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
