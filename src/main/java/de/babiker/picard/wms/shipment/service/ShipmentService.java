package de.babiker.picard.wms.shipment.service;

import de.babiker.picard.wms.enums.ShipmentStatus;
import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.order.repository.OrderRepository;
import de.babiker.picard.wms.packages.PackageEntity;
import de.babiker.picard.wms.packages.repository.PackageRepository;
import de.babiker.picard.wms.shipment.ShipmentEntity;
import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;
import de.babiker.picard.wms.shipment.dto.ShipmentDto;
import de.babiker.picard.wms.shipment.repository.ShipmentMapper;
import de.babiker.picard.wms.shipment.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ShipmentService {
    private final OrderRepository orderRepo;
    private final ShipmentRepository shipmentRepo;
    private final PackageRepository packageRepo;
    private final ShipmentMapper shipmentMapper;

    @Transactional
    public ShipmentEntity createShipmentForOrder(UUID orderId) {

        // pro Auftrag maximal EINE Sendung
        OrderEntity order = orderRepo.findById(orderId).orElseThrow();

        if (order.getShipment() != null) {
            throw new IllegalStateException("Shipment already exists for order");
        }

        ShipmentEntity s = new ShipmentEntity();
        s.setOrder(order);
        shipmentRepo.save(s);

        //initial Paketbildung: ein Paket je Sendung
        PackageEntity p = new PackageEntity();
        p.setShipment(s);
        packageRepo.save(p);

        return s;
    }

    // Paket labeln (tracking)
    @Transactional
    public PackageEntity labelPackage(UUID packageId, String trackingCode, String carrier) {
        //Eindeutigkeit: Tracking-code systemweit
        if (packageRepo.existsByTrackingCode(trackingCode)) {
            throw new IllegalStateException("Tracking code exists");
        }

        PackageEntity p = packageRepo.findById(packageId).orElseThrow();
        p.setTrackingCode(trackingCode);
        p.setCarrier(carrier);

        return packageRepo.save(p);
    }

    //Packen
    @Transactional
    public ShipmentEntity packShipment(UUID shipmentId) {
        ShipmentEntity s = shipmentRepo.findById(shipmentId).orElseThrow();

        boolean allLabeled = s.getPackages() != null &&
        s.getPackages().stream().allMatch(p -> p.getTrackingCode() != null);

        if (!allLabeled) {
            throw new IllegalStateException("Not all packages labeled");
        }

        if (s.getStatus() != ShipmentStatus.CREATED) {
            throw new IllegalStateException("Invalid status for pack");
        }
        s.setStatus(ShipmentStatus.PACKED);
        return shipmentRepo.save(s);
    }

    //Versand
    @Transactional
    public ShipmentEntity shipShipment(UUID shipmentId) {
        ShipmentEntity s = shipmentRepo.findById(shipmentId).orElseThrow();
        if (s.getStatus() != ShipmentStatus.PACKED) throw new
                IllegalStateException("Shipment not packed");
        boolean allLabeled = s.getPackages() != null &&
                s.getPackages().stream().allMatch(p -> p.getTrackingCode() != null);
        if (!allLabeled) {
            throw new IllegalStateException("Not all packages labeled");
        }

        s.setStatus(ShipmentStatus.SHIPPED);
        //Speichern von Versandzeitpunkt
        s.setShippedAt(OffsetDateTime.now());
        return shipmentRepo.save(s);
    }

    public ShipmentDetailDto getShipmentDetail(UUID id) {
        var shipment = shipmentRepo.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("shipment not found"));
        return shipmentMapper.toDetailDto(shipment);
    }

//    public List<ShipmentDetailDto> search(String status, String carrier) {
//        return (shipmentRepo.search(status, carrier)).stream().map(s-> shipmentMapper.toDetailDto(s)).toList();
//    }


    public List<ShipmentDetailDto> searchShipments(String status, String carrier) {
        ShipmentStatus s = status != null ? ShipmentStatus.valueOf(status) : null;
        return shipmentRepo.search(s, carrier).stream()
                .map(shipmentMapper::toDetailDto)
                .toList();
    }
}
