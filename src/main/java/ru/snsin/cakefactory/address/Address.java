package ru.snsin.cakefactory.address;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class Address {
    public static final Address EMPTY_ADDRESS =
            new Address("", "", "");

    @NotBlank
    String addressLine1;
    @NotNull
    String addressLine2;
    @NotNull
    String postcode;
}
