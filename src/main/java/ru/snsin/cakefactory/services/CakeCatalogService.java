package ru.snsin.cakefactory.services;

import ru.snsin.cakefactory.domain.CakeItem;

import java.util.List;

public interface CakeCatalogService {
    List<CakeItem> getAll();
    CakeItem getItemBySku(String sku);
}
