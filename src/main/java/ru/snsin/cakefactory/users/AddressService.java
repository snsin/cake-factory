package ru.snsin.cakefactory.users;

import java.util.Optional;

public interface AddressService {
    Optional<Address> findByUserId(String userId);
    long save(Address address, String userId);
}
