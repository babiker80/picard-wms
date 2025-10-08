package de.babiker.picard.wms.packages.dto;

import java.util.UUID;

public record PackageDto(UUID id, String trackingCode, String carrier) {
}
