package de.babiker.picard.wms.shipment.controller;

import de.babiker.picard.wms.shipment.dto.ShipmentDetailDto;
import de.babiker.picard.wms.shipment.repository.ShipmentMapper;
import de.babiker.picard.wms.shipment.repository.ShipmentRepository;
import de.babiker.picard.wms.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentRepository shipmentRepo;

    //Sendung (aus Auftrag) erzeugen
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> createShipment(@PathVariable UUID orderId) {
        try {
            var s = shipmentService.createShipmentForOrder(orderId);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(ShipmentMapper.toShipmentDto(s)); //201
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(ex.getMessage()); //409
        }
    }

    //Detailabfrage: Sendung
    @GetMapping("/{id}")
    public ResponseEntity<?> getShipment(@PathVariable UUID id) {
//        return shipmentRepo.findById(id).map(s ->
//                ResponseEntity.ok(ShipmentMapper.toDetailDto(s))).orElseGet(() ->
//                ResponseEntity.notFound().build());
        return ResponseEntity.ok(shipmentService.getShipmentDetail(id));
    }

    @GetMapping("/shipments")
    public ResponseEntity<List<ShipmentDetailDto>> searchShipments( //ShipmentListDto
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String carrier) {
        return ResponseEntity.ok(shipmentService.searchShipments(status, carrier));
    }

    @PutMapping("/{id}/pack")
    public ResponseEntity<?> pack(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ShipmentMapper.toShipmentDto(shipmentService.packShipment(id)));
        }
        catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/ship")
    public ResponseEntity<?> ship(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ShipmentMapper.toShipmentDto(shipmentService.shipShipment(id)));
        }
        catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
