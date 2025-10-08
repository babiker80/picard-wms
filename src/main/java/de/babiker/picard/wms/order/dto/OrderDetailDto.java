package de.babiker.picard.wms.order.dto;

import de.babiker.picard.wms.address.dto.AddressDto;
import de.babiker.picard.wms.orderitem.dto.OrderItemDto;
import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;

import java.util.List;
import java.util.UUID;

public record OrderDetailDto(
        UUID id,
        String externalOrderNumber,
        String orderStatus,
        CustomerDto customer,
        AddressDto deliveryAddress,
        List<OrderItemDto> positions,
        ShipmentDetailDto shipment
    ) {
}


