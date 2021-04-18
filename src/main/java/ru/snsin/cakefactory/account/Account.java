package ru.snsin.cakefactory.account;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class Account {
    public static final String ROLE_NAME = "USER";

    @Email
    String email;
    @NotBlank
    String password;
}
