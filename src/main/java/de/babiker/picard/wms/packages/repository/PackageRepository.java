package de.babiker.picard.wms.packages.repository;

import de.babiker.picard.wms.packages.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PackageRepository extends JpaRepository<PackageEntity, UUID> {
    Optional<PackageEntity> findByTrackingCode(String trackingCode);
    boolean existsByTrackingCode(String trackingCode);
}
