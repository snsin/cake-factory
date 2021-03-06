package ru.snsin.cakefactory.account.persistence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_entity")
@Data
@Setter(AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
public class AccountEntity {
    @Id
    private String email;
    @NotBlank
    private String password;
}
