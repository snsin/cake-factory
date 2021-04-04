package ru.snsin.cakefactory.users.persistence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address_entity")
@Data
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String addressLine1;
    @NotNull
    private String addressLine2;
    @NotNull
    private String postcode;
    @NotBlank
    private String userId;
}
