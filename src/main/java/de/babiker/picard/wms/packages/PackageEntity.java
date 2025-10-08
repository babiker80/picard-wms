package de.babiker.picard.wms.packages;

import de.babiker.picard.wms.shipment.ShipmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "packages", uniqueConstraints = @UniqueConstraint(columnNames
        = {"tracking_code"}))
public class PackageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private ShipmentEntity shipment;

    //Eindeutigkeit: Tracking-code
    @Column(name = "tracking_code", unique = true)
    private String trackingCode;

    private String carrier;
}
