package de.babiker.picard.wms.product.dto;

import java.util.UUID;

public record ProductDto(UUID id, String sku, String name, double price) {
}
