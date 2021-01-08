package ru.snsin.cakefactory.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.BasketService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
@SessionScope
public class BasketServiceImpl implements BasketService {

    private final List<CakeItem> items = new ArrayList<>();

    @Override
    public int countItems() {
        return items.size();
    }

    @Override
    public void addItem(CakeItem cake) {
        items.add(Objects.requireNonNull(cake));
    }

    @Override
    public List<CakeItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public List<BasketItem> getNameCountPairs() {
        final Map<String, Integer> countedItems = items.stream()
                .collect(groupingBy(CakeItem::getName, summingInt(item -> 1)));
        return countedItems.entrySet().stream()
                .map(nameCount -> new BasketItem(nameCount.getKey(), nameCount.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }
}
