package de.babiker.picard.wms.packages.controller;

import de.babiker.picard.wms.packages.dto.LabelRequest;
import de.babiker.picard.wms.packages.repository.PackageRepository;
import de.babiker.picard.wms.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/packages")
public class PackageController {

    private final ShipmentService shipmentService;
    private final PackageRepository packageRepo;

    //paket(e) labeln (tracking)
    @PostMapping("/{id}/label")
    public ResponseEntity<?> label(@PathVariable UUID id, @RequestBody LabelRequest req) {
        try {
            var p = shipmentService.labelPackage(id, req.trackingCode(), req.carrier());
            return ResponseEntity.ok(p);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(ex.getMessage()); //409
        }
    }
}
