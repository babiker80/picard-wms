package de.babiker.picard.wms.orderitem.dto;

import de.babiker.picard.wms.product.dto.ProductDto;

import java.util.UUID;

public record OrderItemDto(UUID id, ProductDto product, int quantity, double unitPrice, double totalPrice) {
}
