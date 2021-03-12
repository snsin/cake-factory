package ru.snsin.cakefactory.users;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class Account {
    public static final String ROLE_NAME = "USER";
    public static final GrantedAuthority ACCOUNT_AUTHORITY =
            new SimpleGrantedAuthority("ROLE_" + ROLE_NAME);

    @Email
    String email;
    @NotBlank
    String password;
}
