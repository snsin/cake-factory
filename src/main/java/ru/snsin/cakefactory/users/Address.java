package ru.snsin.cakefactory.users;

import lombok.Value;

@Value
public class Address {
    String addressLine1;
    String addressLine2;
    String postcode;
}
