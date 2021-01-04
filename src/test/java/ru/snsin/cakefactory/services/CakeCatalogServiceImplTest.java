package ru.snsin.cakefactory.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.snsin.cakefactory.domain.CakeItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CakeCatalogServiceImplTest {

    private final CakeCatalogService cakeCatalog = new CakeCatalogServiceImpl();

    @Test
    void shouldGetAllCakeItems() {

        final Map<String, CakeItem> expectedCakesByName = Arrays.stream(new CakeItem[] {
                new CakeItem("All Butter Croissant", new BigDecimal("0.75")),
                new CakeItem("Chocolate Croissant", new BigDecimal("0.95")),
                new CakeItem("Fresh Baguette", new BigDecimal("1.60")),
                new CakeItem("Red Velvet", new BigDecimal("3.95")),
                new CakeItem("Victoria Sponge", new BigDecimal("5.45")),
                new CakeItem("Carrot Cake", new BigDecimal("3.45"))
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
}