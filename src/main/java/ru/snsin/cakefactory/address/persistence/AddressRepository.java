package ru.snsin.cakefactory.address.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByUserId(String userId);
}
