package ru.snsin.cakefactory.domain;

import lombok.Value;

@Value
public class BasketItem {
    CakeItem cake;
    Integer count;
}
