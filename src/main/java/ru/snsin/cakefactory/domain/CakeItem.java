package ru.snsin.cakefactory.domain;

import java.math.BigDecimal;

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
}
