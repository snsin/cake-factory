package ru.snsin.cakefactory.users;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class Address {
    @NotBlank
    String addressLine1;
    @NotBlank
    String addressLine2;
    @NotBlank
    String postcode;
}
