package ru.snsin.cakefactory.users.persistence;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.snsin.cakefactory.users.Address;
import ru.snsin.cakefactory.users.AddressService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaAddressServiceImplTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AddressRepository addressRepository;

    private AddressService addressService;
    private Faker faker;

    @BeforeEach
    void setUp() {
        addressService = new JpaAddressServiceImpl(addressRepository);
        faker = new Faker();
    }

    @Test
    void shouldFindAddressUserById() {
        final String email = "user-id@mail.ru";
        AddressEntity addressEntity = createAddress(email);

        final Optional<Address> userAddress = addressService.findByUserId(email);

        final Address address = assertDoesNotThrow(() -> userAddress.orElseThrow(RuntimeException::new));
        assertEquals(addressEntity.getAddressLine1(), address.getAddressLine1());
        assertEquals(addressEntity.getAddressLine2(), address.getAddressLine2());
        assertEquals(addressEntity.getPostcode(), address.getPostcode());
    }

    @Test
    void shouldReturnEmptyOptionalWhenDoesNotFindAddress() {
        String email = "lost-email@example.com";

        final Optional<Address> notFoundAddress = addressService.findByUserId(email);

        assertTrue(notFoundAddress.isEmpty());
    }

    @Test
    void shouldSaveIfAddressExists() {
        String email = faker.internet().safeEmailAddress();
        AddressEntity addressEntity = createAddress(email);
        Address expectedAddress = new Address("Line 1", "Line 2", "postcode");

        final long savedAddressId = addressService.save(expectedAddress, email);

        assertEquals(addressEntity.getId(), savedAddressId);

        final Optional<Address> savedAddress = addressService.findByUserId(email);

        final Address actualAddress = assertDoesNotThrow(() -> savedAddress.orElseThrow(RuntimeException::new));
        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    void shouldSaveIfAddressDoesNotExist() {
        String email = "should-not-exist@mail.ru";

        final Optional<Address> notExist = addressService.findByUserId(email);
        assertTrue(notExist.isEmpty());

        final String addressLine1 = "line 1";
        final String addressLine2 = "line two";
        final String postcode = "postcode";

        final long addressId = addressService.save(new Address(addressLine1, addressLine2, postcode), email);
        assertTrue(addressId >= 0);

        final AddressEntity actualAddressEntity = entityManager.find(AddressEntity.class, addressId);

        assertEquals(addressLine1, actualAddressEntity.getAddressLine1());
        assertEquals(addressLine2, actualAddressEntity.getAddressLine2());
        assertEquals(postcode, actualAddressEntity.getPostcode());
    }

    private AddressEntity createAddress(String userId) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1(faker.address().streetAddress());
        addressEntity.setAddressLine2(faker.address().secondaryAddress());
        addressEntity.setPostcode(faker.address().zipCode());
        addressEntity.setUserId(userId);
        return entityManager.persistAndFlush(addressEntity);
    }
}