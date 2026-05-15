package com.ksef.metadata_service.repository;

import com.ksef.metadata_service.entity.DokumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetadataRepository  extends JpaRepository<DokumentMetadata, Long> {
    Optional< DokumentMetadata> findByDokumentId(Long dokumentId);

    Optional<DokumentMetadata> findByNumerFaktury(String numer);
}
