package com.ksef.metadata_service.service;

import com.ksef.metadata_service.entity.DokumentMetadata;
import com.ksef.metadata_service.enumerator.StorageStatus;
import com.ksef.metadata_service.event.PdfUploadEvent;
import com.ksef.metadata_service.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MetadataService {
    private final MetadataRepository repo;

    public void save(PdfUploadEvent event){
        DokumentMetadata metadata =  DokumentMetadata.builder()
                .dokumentId(event.getDokumentId())
                .numerFaktury(event.getNumerFaktury())
                .bucketName(event.getBucketName())
                .objectName(event.getBucketName())
                .mimeType(event.getMimeType())
                .createdAt(LocalDateTime.now())
                .status(StorageStatus.STORED)
                .build();

        repo.save(metadata);
    }

    public DokumentMetadata findByDokumentId(Long dokumentId){
        return repo.findByDokumentId(dokumentId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Metadata nie istnieje"
                        )
                );
    }


    public DokumentMetadata findByNumerFaktury(
            String numer
    ){

        return repo.findByNumerFaktury(
                numer
        ).orElseThrow(
                () -> new RuntimeException(
                        "Metadata nie istnieją"
                )
        );
    }
}
