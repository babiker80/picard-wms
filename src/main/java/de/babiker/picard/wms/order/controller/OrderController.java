package de.babiker.picard.wms.order.controller;

import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.order.dto.CreateOrderRequest;
import de.babiker.picard.wms.order.dto.OrderDto;
import de.babiker.picard.wms.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    //Auftrag erfassen
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest req) {
        OrderEntity e = new OrderEntity();
        e.setExternalOrderNumber(req.externalOrderNumber());
        e.setDeliveryAddress(req.deliveryAddress());
        e.setPositions(req.positions());
        try {
            OrderDto saved = orderService.createOrder(e);
            return ResponseEntity.created(URI.create("/api/orders/" +
                    saved.id())).body(saved);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).build(); //409
        }
    }

    //Detailabfrage: Auftrag
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(orderService.getOrderDetail(id));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
