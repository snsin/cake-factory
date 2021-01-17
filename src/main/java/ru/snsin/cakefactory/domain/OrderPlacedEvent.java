package ru.snsin.cakefactory.domain;

import lombok.Value;

import java.util.List;

@Value
public class OrderPlacedEvent {
    OrderInfo orderInfo;
    List<BasketItem> basketItems;
}
