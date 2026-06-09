package com.ksef.storage_service.service;


import com.ksef.storage_service.config.RestTemplateConfig;
import com.ksef.storage_service.consumer.PdfStoredProducer;
import com.ksef.storage_service.dto.DocumentMetadataResponse;
import com.ksef.storage_service.event.PdfGeneratedEvent;
import com.ksef.storage_service.event.PdfUploadEvent;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private static final String BUCKET_NAME = "faktury";
    private static final String MIME_TYPE = "application/pdf";

    private final MinioClient client;
    private final PdfStoredProducer producer;
    private final RestTemplateConfig restTemplate;

    // do poprawienia po zmianie sposobu pobierania i dodawania plików do minio
    public void uploadPdf(PdfGeneratedEvent event) {
        try {
            File file = new File(event.getFilepath());

            if (!file.exists()) {
                throw new RuntimeException(
                        "Plik PDF nie istnieje: "
                                + event.getFilepath()
                );
            }

            boolean exists = client.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(BUCKET_NAME)
                            .build()
            );

            if (!exists) {
                client.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(BUCKET_NAME)
                                .build()
                );
            }

            String objectName =
                    event.getNumerFaktury()
                            .replace("/", "_")
                            + ".pdf";

            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .filename(file.getAbsolutePath())
                            .contentType(MIME_TYPE)
                            .build()
            );

            log.info("PDF zapisany w MinIO: {}/{}", BUCKET_NAME, objectName);

            producer.send(
                    new PdfUploadEvent(
                            event.getDokumentId(),
                            event.getNumerFaktury(),
                            BUCKET_NAME,
                            objectName,
                            MIME_TYPE,
                            ownerId,
                            file.length()
                    )
            );

            log.info("Wysłano event pdf-stored dla {}", event.getNumerFaktury());

        } catch (Exception e) {
            log.error("Błąd uploadu PDF", e);
        }
    }

    public InputStream downloadPdfByDocumentID(Long documentId, String ownerId) {
        try {
            DocumentMetadataResponse metadata = restTemplate.getForObject(
                    "http://localhost:8083/api/v1/metadata/document/" + dokumentId,
                    DocumentMetadataResponse.class
            );

            if (metadata == null){
                throw new RuntimeException("Metadata nie istnieje !");
            }

            if(!metadata.getOwnerId().equals(ownerId)){
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Nie masz dostępu do tego zasobu !"
                );
            }

            return client.getObject(
                    GetObjectArgs.builder()
                            .bucket(metadata.getBucketName())
                            .object(metadata.getObjectName())
                            .build()
            );
        } catch (ResponseStatusException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Błąd pobierania PDF z MinIO", e);
        }
    }

}
