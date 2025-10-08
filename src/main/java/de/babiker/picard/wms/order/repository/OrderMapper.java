package de.babiker.picard.wms.order.repository;

import de.babiker.picard.wms.address.dto.AddressDto;
import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.order.dto.CustomerDto;
import de.babiker.picard.wms.order.dto.OrderDetailDto;
import de.babiker.picard.wms.order.dto.OrderDto;
import de.babiker.picard.wms.orderitem.dto.OrderItemDto;
import de.babiker.picard.wms.product.dto.ProductDto;
import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;
import de.babiker.picard.wms.shipment.repository.ShipmentMapper;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderMapper {
    private final ShipmentMapper shipmentMapper;

    public OrderDto toOrderDto(OrderEntity e) {
        return new OrderDto(e.getId(), e.getExternalOrderNumber(),
                e.getDeliveryAddress());
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
        var items = o.getPositions().stream().map(i ->
                new OrderItemDto(
                        i.getId(),
                        new ProductDto(i.getProduct().getId(), i.getProduct().getSku(), i.getProduct().getName(), i.getProduct().getPrice()),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getTotalPrice()
                )).toList();

        ShipmentDetailDto shipmentDto = null;
        if (o.getShipment() != null) {
            shipmentDto = shipmentMapper.toDetailDto(o.getShipment());
        }

        return new OrderDetailDto(o.getId(), o.getExternalOrderNumber(), o.getStatus().name(), customer, address, items, shipmentDto);
    }
}
