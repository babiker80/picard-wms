package de.babiker.picard.wms.order.repository;

import de.babiker.picard.wms.address.dto.AddressDto;
import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.order.dto.CustomerDto;
import de.babiker.picard.wms.order.dto.OrderDetailDto;
import de.babiker.picard.wms.order.dto.OrderDto;
import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;

public class OrderMapper {
    public static OrderDto toOrderDto(OrderEntity e) {
        return new OrderDto(e.getId(), e.getExternalOrderNumber(),
                e.getDeliveryAddress());
    }

    public static OrderDetailDto toDetailDto(OrderEntity e) {
        return new OrderDetailDto(e.getId(), e.getExternalOrderNumber(),
                e.getStatus(), e.getCustomer(), e.getDeliveryAddress(),
                e.getPositions(), e.getShipment());
    }
    public OrderDetailDto toOrderDetail(OrderEntity o) {
        var customer = new CustomerDto(
                o.getCustomer().getId(),
                o.getCustomer().getName(),
                o.getCustomer().getEmail(),
                o.getCustomer().getPhone()
        );
        var address = new AddressDto(
                o.getDeliveryAddress().getId(),
                o.getDeliveryAddress().getStreet(),
                o.getDeliveryAddress().getCity(),
                o.getDeliveryAddress().getPostalCode(),
                o.getDeliveryAddress().getCountry()
        );
        var items = o.getItems().stream().map(i ->
                new OrderItemDto(
                        i.getId(),
                        new ProductDto(i.getProduct().getId(), i.getProduct().getSku(), i.getProduct().getName(), i.getProduct().getPrice()),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getTotalPrice()
                )).collect(Collectors.toList());

        ShipmentDetailDto shipmentDto = null;
        if (o.getShipment() != null) {
            shipmentDto = toShipmentDetail(o.getShipment());
        }

        return new OrderDetailDto(o.getId(), o.getExternalOrderNumber(), customer, address, items, shipmentDto);
    }
}
