package de.babiker.picard.wms.order.dto;

import de.babiker.picard.wms.address.AddressEntity;
import de.babiker.picard.wms.orderitem.OrderItemEntity;

import java.util.List;

public record CreateOrderRequest(String externalOrderNumber, AddressEntity
deliveryAddress, List<OrderItemEntity> positions) {
    //List<Object> positions
}
