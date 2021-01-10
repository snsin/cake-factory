package ru.snsin.cakefactory.domain;

import lombok.Data;

@Data
public class OrderInfo {
    private String addressLine1;
    private String addressLine2;
    private String postCode;
}
