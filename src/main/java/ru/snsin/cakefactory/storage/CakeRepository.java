package ru.snsin.cakefactory.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CakeRepository extends CrudRepository<CakeEntity, String> {
    @NonNull
    List<CakeEntity> findAll();
}
