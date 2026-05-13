package com.ksef.storage_service.service;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    private final MinioClient client;

    public void uploadPdf(String path, String numerFaktury){
        try{
            boolean exist = client.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket("faktury").build()
            );

            if(!exist){
                client.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket("faktury")
                                .build()
                );
            }

            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("faktury")
                            .object(
                                    numerFaktury
                                            .replace("/", "_")
                                            + ".pdf"
                            )
                            .filename(path)
                            .build()
            );
            log.info("PDF wrzucony do MinIO");

        } catch (Exception e){
            log.error(
                    "Błąd uploadu PDF",
                    e
            );
        }
    }
}
