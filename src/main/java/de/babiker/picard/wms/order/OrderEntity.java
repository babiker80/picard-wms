package de.babiker.picard.wms.order;

import de.babiker.picard.wms.address.AddressEntity;
import de.babiker.picard.wms.customer.CustomerEntity;
import de.babiker.picard.wms.enums.OrderStatus;
import de.babiker.picard.wms.enums.ShipmentStatus;
import de.babiker.picard.wms.orderitem.OrderItemEntity;
import de.babiker.picard.wms.shipment.ShipmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = {"external_order_number"}))
public class OrderEntity {
    @Id
    @GeneratedValue
    private UUID id;

    //Deduplizierung
    @Column(name = "external_order_number", nullable = false, unique = true)
    private String externalOrderNumber;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity deliveryAddress;

    //@Column(columnDefinition = "jsonb") //we can also use: simple JSON string for MVP
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> positions;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ShipmentEntity shipment;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    //Auftrag startet im Status CREATED
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;
}

