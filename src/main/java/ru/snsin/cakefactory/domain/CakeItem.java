package ru.snsin.cakefactory.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class CakeItem {

    private final String name;
    private final BigDecimal price;


    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CakeItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CakeItem cakeItem = (CakeItem) o;
        return Objects.equals(name, cakeItem.name) && Objects.equals(price, cakeItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
