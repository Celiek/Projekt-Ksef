package com.ksef.storage_service.service;

import com.ksef.storage_service.event.PdfGeneratedEvent;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient client;

    // TODO
    // Dodać randomowe generowanie nazwy plików

    public void uploadPdf(PdfGeneratedEvent event){
        try{
            File file = new File(event.getFilePath());

            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("ksef-pdf")
                            .object(file.getName())
                            .filename(file.getAbsolutePath())
                            .build()
            );
        } catch (Exception e){
            log.error("Wystapił błąd uploadu", e);
        }
    }
}