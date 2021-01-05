package ru.snsin.cakefactory.services;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.storage.CakeEntity;
import ru.snsin.cakefactory.storage.CakeRepository;

import java.math.BigDecimal;
import java.util.List;
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
                .map(this::cakeItemFromCakeEntity)
                .collect(Collectors.toUnmodifiableList());
//        return Collections.emptyList();
    }

    private  CakeItem cakeItemFromCakeEntity(CakeEntity entity) {
        return new CakeItem(entity.getName(), entity.getPrice());
    }
}
