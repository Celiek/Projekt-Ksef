package com.ksef.storage_service.service;


import com.ksef.storage_service.consumer.PdfStoredProducer;
import com.ksef.storage_service.event.PdfGeneratedEvent;
import com.ksef.storage_service.event.PdfUploadEvent;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                            file.length()
                    )
            );

            log.info("Wysłano event pdf-stored dla {}", event.getNumerFaktury());

        } catch (Exception e) {
            log.error("Błąd uploadu PDF", e);
        }
    }

    public InputStream downloadPdf(String objectName) {
        try {
            return client.getObject(
                    GetObjectArgs.builder()
                            .bucket("faktury")
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Błąd pobierania PDF z MinIO", e);
        }
    }

}
