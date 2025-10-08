package de.babiker.picard.wms.order.service;

import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.order.dto.OrderDetailDto;
import de.babiker.picard.wms.order.repository.OrderMapper;
import de.babiker.picard.wms.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;

    public OrderEntity createOrder(OrderEntity order) {
        if (orderRepo.existsByExternalOrderNumber(order.getExternalOrderNumber())) {
            throw new IllegalStateException("Duplicate externalOrderNumber");
        }
        return orderRepo.save(order);
    }

    public OrderDetailDto getOrderDetail(UUID id) {
        var order = orderRepo.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toDetailDto(order);
    }
}
