package de.babiker.picard.wms.shipment.dto;

import de.babiker.picard.wms.packages.dto.PackageDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ShipmentDetailDto(UUID id, String status, OffsetDateTime shippedAt,
                                String orderId, List<PackageDto> packages) {
}
