package de.babiker.picard.wms.shipment.repository;

import de.babiker.picard.wms.packages.dto.PackageDto;
import de.babiker.picard.wms.shipment.ShipmentEntity;
import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;
import de.babiker.picard.wms.shipment.dto.ShipmentDto;

import java.util.List;
import java.util.stream.Collectors;

public class ShipmentMapper {

    public static ShipmentDto toShipmentDto(ShipmentEntity s) {
        List<PackageDto> packages = s.getPackages() == null ? java.util.List.of() :
                s.getPackages().stream()
                .map(p -> new PackageDto(p.getId(), p.getTrackingCode(),
                        p.getCarrier())).collect(Collectors.toList());

        return new ShipmentDto(s.getId(), s.getStatus().name(),
                s.getShippedAt(), packages);
    }

    public ShipmentDetailDto toDetailDto(ShipmentEntity s) {
        List<PackageDto> packages = s.getPackages() == null ? java.util.List.of() :
                s.getPackages().stream()
                        .map(p -> new PackageDto(p.getId(), p.getTrackingCode(),
                                p.getCarrier())).collect(Collectors.toList());

        return new ShipmentDetailDto(s.getId(), s.getStatus().name(),
                s.getShippedAt(), s.getOrder().getId().toString(), packages);
    }
}
