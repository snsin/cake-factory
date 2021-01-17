package ru.snsin.cakefactory.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class OrderInfo {
    @NotBlank String addressLine1;
    @NotBlank String addressLine2;
    @NotBlank String postCode;
}
