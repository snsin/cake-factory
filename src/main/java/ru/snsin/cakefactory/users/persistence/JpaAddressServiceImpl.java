package ru.snsin.cakefactory.users.persistence;

import ru.snsin.cakefactory.users.Address;
import ru.snsin.cakefactory.users.AddressService;

import java.util.Optional;

public class JpaAddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public JpaAddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> findByUserId(String userId) {
        return Optional.empty();
    }

    @Override
    public long save(Address address, String userId) {
        return 0;
    }
}
