package ru.snsin.cakefactory.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CakeItem {

    String sku;
    String name;
    BigDecimal price;

}
