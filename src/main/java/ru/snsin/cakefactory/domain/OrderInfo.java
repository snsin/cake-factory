package ru.snsin.cakefactory.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderInfo {
    @NotBlank
    private String addressLine1;
    @NotBlank
    private String addressLine2;
    @NotBlank
    private String postCode;
}
