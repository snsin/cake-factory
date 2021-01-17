package ru.snsin.cakefactory.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.services.CakeCatalogService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CakeCatalogServiceImplTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private CakeRepository cakeRepository;

    private CakeCatalogService cakeCatalog;

    @BeforeEach
    void setUp() {
        cakeCatalog = new CakeCatalogServiceImpl(cakeRepository);
    }


    @Test
    @DisplayName("migrations work")
    void shouldGetAllCakeItems() {

        final Map<String, CakeItem> expectedCakesByName = Arrays.stream(new CakeItem[] {
                new CakeItem("abcr", "All Butter Croissant", new BigDecimal("0.75")),
                new CakeItem("ccr", "Chocolate Croissant", new BigDecimal("0.95")),
                new CakeItem("b", "Fresh Baguette", new BigDecimal("1.60")),
                new CakeItem("rv", "Red Velvet", new BigDecimal("3.95")),
                new CakeItem("vs","Victoria Sponge", new BigDecimal("5.45")),
                new CakeItem("cc", "Carrot Cake", new BigDecimal("3.45"))
        }).collect(Collectors.toUnmodifiableMap(CakeItem::getName, Function.identity()));

        Map<String, CakeItem> actualCakesByName = cakeCatalog.getAll().stream()
                .collect(Collectors.toUnmodifiableMap(CakeItem::getName, Function.identity()));

        assertEquals(expectedCakesByName.size(), actualCakesByName.size());
        assertAll(expectedCakesByName.entrySet().stream()
                .map(expectedCake -> () -> {
                    final BigDecimal actualPrice = actualCakesByName.get(expectedCake.getKey()).getPrice();
                    assertEquals(0, actualPrice.compareTo(expectedCake.getValue().getPrice()));
                }));
    }

    @Test
    void shouldSaveAndReturnData() {
        String expectedName = "Red Velvet";
        String expectedId = saveCake(expectedName, new BigDecimal("3.45"));

        List<CakeItem> items = cakeCatalog.getAll();

        assertTrue(items.stream()
                .anyMatch(cakeItem ->
                        expectedName.equals(cakeItem.getName()) && expectedId.equals(cakeItem.getSku())));

    }

    @Test
    void shouldGetCakeBySku() {
        String expectedName = "Carrot Cake";
        String expectedId = saveCake(expectedName, new BigDecimal("4.45"));

        CakeItem actualCake = cakeCatalog.getItemBySku(expectedId);
        assertEquals(expectedId, actualCake.getSku());
        assertEquals(expectedName, actualCake.getName());
    }

    private String saveCake(String name, BigDecimal price) {
        CakeEntity savedCake = new CakeEntity();
        String id = UUID.randomUUID().toString().replace("-", "");
        savedCake.setId(id);
        savedCake.setName(name);
        savedCake.setPrice(price);

        entityManager.persistAndFlush(savedCake);

        return id;
    }
}
