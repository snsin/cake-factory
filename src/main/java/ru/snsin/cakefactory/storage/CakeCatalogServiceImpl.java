package ru.snsin.cakefactory.storage;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

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
                .map(entity -> new CakeItem(entity.getName(), entity.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }
}
