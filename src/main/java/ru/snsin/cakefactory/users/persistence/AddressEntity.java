package ru.snsin.cakefactory.users.persistence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String addressLine2;
    @NotBlank
    private String postcode;
    @NotBlank
    private String userId;
}
