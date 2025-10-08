package de.babiker.picard.wms.address.dto;

import java.util.UUID;

public record AddressDto(UUID id, String street, String city, String postalCode, String country) {
}
