package ru.snsin.cakefactory.users;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class Account {
    @Email
    String email;
    @NotBlank
    String password;
}