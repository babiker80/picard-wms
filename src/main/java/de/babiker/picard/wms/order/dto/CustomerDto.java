package de.babiker.picard.wms.order.dto;

import java.util.UUID;

public record CustomerDto(UUID id, String name, String email, String phone) {
}
