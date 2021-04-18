package ru.snsin.cakefactory.domain;

import lombok.Value;
import ru.snsin.cakefactory.address.Address;

import java.util.List;

@Value
public class OrderPlacedEvent {
    Address address;
    List<BasketItem> basketItems;
}
