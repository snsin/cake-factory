package ru.snsin.cakefactory.services;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;

import java.util.Collections;
import java.util.List;

@Service
public class CakeCatalogServiceImpl implements CakeCatalogService {
    @Override
    public List<CakeItem> getAll() {
        return Collections.emptyList();
    }
}
