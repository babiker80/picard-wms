package de.babiker.picard.wms.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity {
    private UUID id;
    private String sku;
    private String name;
    private double price;
}
