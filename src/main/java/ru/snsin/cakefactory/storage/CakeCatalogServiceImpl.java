package ru.snsin.cakefactory.storage;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CakeCatalogServiceImpl implements CakeCatalogService {
    private final CakeRepository cakeRepository;

    public CakeCatalogServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public List<CakeItem> getAll() {
        return cakeRepository.findAll().stream()
                .map(this::createCakeFromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CakeItem getItemBySku(String sku) {
        CakeEntity entity = cakeRepository.findById(sku)
                .orElseThrow(NoSuchElementException::new);
        return createCakeFromEntity(entity);
    }

    private CakeItem createCakeFromEntity(CakeEntity entity) {
        return new CakeItem(entity.getId(), entity.getName(), entity.getPrice());
    }
}
