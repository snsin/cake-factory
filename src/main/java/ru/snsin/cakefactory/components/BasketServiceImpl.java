package ru.snsin.cakefactory.components;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.BasketService;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Component
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class BasketServiceImpl implements BasketService {

    private final List<CakeItem> items = new ArrayList<>();
    private final CakeCatalogService cakeCatalog;

    public BasketServiceImpl(CakeCatalogService cakeCatalog) {
        this.cakeCatalog = cakeCatalog;
    }

    @Override
    public int countItems() {
        return items.size();
    }

    @Override
    public void addItem(String sku) {
        final CakeItem cake = cakeCatalog.getItemBySku(sku);
        items.add(cake);
    }

    @Override
    public List<CakeItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public List<BasketItem> getBasketItems() {
        final Map<CakeItem, Integer> countedItems = items.stream()
                .collect(groupingBy(Function.identity(), summingInt(item -> 1)));
        return countedItems.entrySet().stream()
                .map(nameCount -> new BasketItem(nameCount.getKey(), nameCount.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void removeItem(String sku) {
        for (Iterator<CakeItem> i = items.iterator(); i.hasNext();) {
            CakeItem currentItem = i.next();
            if (currentItem.getSku().equals(sku)) {
                i.remove();
                return;
            }
        }
    }

    @Override
    public void clearBasket() {
        items.clear();
    }
}
