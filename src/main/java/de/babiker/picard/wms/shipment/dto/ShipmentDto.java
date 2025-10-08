package de.babiker.picard.wms.shipment.dto;

import de.babiker.picard.wms.packages.dto.PackageDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ShipmentDto(UUID id, String status, OffsetDateTime shippedAt,
                          List<PackageDto> packages) {
}
