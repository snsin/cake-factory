package ru.snsin.cakefactory.address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> findByUserId(String userId);
    long save(Address address, String userId);
}
