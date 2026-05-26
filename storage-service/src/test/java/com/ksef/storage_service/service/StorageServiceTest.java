package com.ksef.storage_service.service;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @Mock
    private MinioClient client;
    @InjectMocks
    private StorageService underTest;

    @Test
    void uploadPdf_shouldHandleException() throws Exception{
        // given
        when(client.bucketExists(
                any(BucketExistsArgs.class)))
                .thenThrow(
                        new RuntimeException(
                                "Minio error"
                        )
                );

        // when + then
        underTest.uploadPdf(
                "/tmp/test.pdf",
                "FV/1/2026"
        );

        verify(client)
                .bucketExists(
                        any(
                                BucketExistsArgs.class
                        )
                );
    }
}