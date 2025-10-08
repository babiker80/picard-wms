package de.babiker.picard.wms.order.repository;

import de.babiker.picard.wms.order.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByExternalOrderNumber(String externalOrderNumber);
    boolean existsByExternalOrderNumber(String externalOrderNumber);
    Optional<OrderEntity> findByIdWithRelations(UUID id);

    @EntityGraph(attributePaths = {
            "customer",
            "deliveryAddress",
            "items.product",
            "shipment.packages"
    })
    Optional<OrderEntity> findById(UUID id);
}
