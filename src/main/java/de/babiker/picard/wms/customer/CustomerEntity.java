package de.babiker.picard.wms.customer;

import de.babiker.picard.wms.address.AddressEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String contactPerson;

    private AddressEntity billingAddresses;

    private AddressEntity deliveryAddresses;

    private LocalDateTime created;

    private LocalDateTime updated;
}
