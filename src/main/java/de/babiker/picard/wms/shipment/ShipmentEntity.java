package de.babiker.picard.wms.shipment;

import de.babiker.picard.wms.enums.ShipmentStatus;
import de.babiker.picard.wms.order.OrderEntity;
import de.babiker.picard.wms.packages.PackageEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private OrderEntity order;

    //Sendung startet im Status CREATED
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.CREATED;

    private OffsetDateTime shippedAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PackageEntity> packages;
}
