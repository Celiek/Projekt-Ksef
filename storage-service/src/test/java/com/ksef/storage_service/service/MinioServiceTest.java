package com.ksef.storage_service.service;

import com.ksef.storage_service.event.PdfGeneratedEvent;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MinioServiceTest {

    @Mock
    private MinioClient minioClient;
    @InjectMocks
    private MinioService minioService;

    @Test
    void uploadPdf_shouldUploadFileToMinio() throws Exception {
        //given
        File file = File.createTempFile(
                "test-pdf",
                ".pdf"
        );

        PdfGeneratedEvent event = new PdfGeneratedEvent();

        event.setFilePath(file.getAbsolutePath());
        ArgumentCaptor<UploadObjectArgs> captor =
                ArgumentCaptor.forClass(UploadObjectArgs.class);

        //when
        minioService.uploadPdf(event);
        //then
        verify(minioClient).uploadObject(captor.capture());
        UploadObjectArgs args = captor.getValue();

        assertEquals("ksef-pdf", args.bucket());

        assertEquals(file.getAbsolutePath(), args.filename());
    }

    @Test
    void uploadPdf_shouldHandleException()
            throws Exception {

        // given
        File file =
                File.createTempFile(
                        "test",
                        ".pdf"
                );

        PdfGeneratedEvent event =
                new PdfGeneratedEvent();

        event.setFilePath(
                file.getAbsolutePath()
        );

        doThrow(new RuntimeException(
                "Minio error"))
                .when(minioClient)
                .uploadObject(
                        any(UploadObjectArgs.class)
                );

        // when
        minioService.uploadPdf(event);

        // then
        verify(minioClient)
                .uploadObject(
                        any(
                                UploadObjectArgs.class
                        )
                );
    }
}