package de.babiker.picard.wms.order.dto;

import de.babiker.picard.wms.address.AddressEntity;

import java.util.UUID;

public record OrderDto(UUID id, String externalOrderNumber, AddressEntity deliveryAddress) {
    //String deliveryAddress
}
