package de.babiker.picard.wms.shipment.repository;

import de.babiker.picard.wms.enums.ShipmentStatus;
import de.babiker.picard.wms.shipment.ShipmentEntity;
import de.babiker.picard.wms.shipment.dto.ShipmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity,UUID> {
    List<ShipmentEntity> findByStatus(ShipmentStatus status);
    Optional<ShipmentEntity> findByIdWithRelations(UUID id);



    @Query("""
SELECT s FROM Shipment s
LEFT JOIN FETCH s.packages p
WHERE (:status IS NULL OR s.status = :status)
  AND (:carrier IS NULL OR p.carrier = :carrier)
""")
    List<ShipmentEntity> search(@Param("status") String status, @Param("carrier") String carrier);

    @Query("""
        SELECT DISTINCT s FROM ShipmentEntity s
        LEFT JOIN FETCH s.packages
        LEFT JOIN FETCH s.order o
        WHERE (:status IS NULL OR s.status = :status)
        AND (:carrier IS NULL OR EXISTS (
            SELECT 1 FROM PackageEntity p WHERE p.shipment = s AND p.carrier = :carrier
        ))
    """)
    List<ShipmentEntity> search(@Param("status") ShipmentStatus status, @Param("carrier") String carrier);

    @Query("""
        SELECT s FROM ShipmentEntity s
        LEFT JOIN FETCH s.packages
        LEFT JOIN FETCH s.order
        WHERE s.id = :id
    """)
    Optional<ShipmentEntity> findDetailById(@Param("id") UUID id);
}
