package de.babiker.picard.wms.address;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressEntity {
    private UUID id;
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;
    private String country;
}
