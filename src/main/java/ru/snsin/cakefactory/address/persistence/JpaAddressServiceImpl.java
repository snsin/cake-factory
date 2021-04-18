package ru.snsin.cakefactory.address.persistence;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.address.AddressService;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class JpaAddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public JpaAddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> findByUserId(String userId) {
        return addressRepository.findByUserId(userId).map(this::mapEntityToAddress);
    }

    @Override
    public long save(Address address, String userId) {
        if (address == null || !hasText(userId)){
            return -1L;
        }
        final AddressEntity addressEntity = addressRepository.findByUserId(userId)
                .map((savedEntity) -> updateEntityFromAddress(savedEntity, address))
                .orElse(mapAddressToEntity(address));
        addressEntity.setUserId(userId);
        return addressRepository.save(addressEntity).getId();
    }

    private AddressEntity updateEntityFromAddress(AddressEntity savedEntity, Address address) {
        savedEntity.setAddressLine1(address.getAddressLine1());
        savedEntity.setAddressLine2(address.getAddressLine2());
        savedEntity.setPostcode(address.getPostcode());
        return savedEntity;
    }

    private AddressEntity mapAddressToEntity(Address address) {
        AddressEntity newAddress = new AddressEntity();
        newAddress.setAddressLine1(address.getAddressLine1());
        newAddress.setAddressLine2(address.getAddressLine2());
        newAddress.setPostcode(address.getPostcode());
        return newAddress;
    }

    private Address mapEntityToAddress(AddressEntity addressEntity) {
        return new Address(addressEntity.getAddressLine1(),
                addressEntity.getAddressLine2(),
                addressEntity.getPostcode());
    }
}
