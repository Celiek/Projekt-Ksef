package com.ksef.storage_service.consumer;

import com.ksef.storage_service.event.PdfGeneratedEvent;
import com.ksef.storage_service.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PdfGeneratedConsumer {
    private final MinioService service;

    @KafkaListener(topics = "pdf-generated",groupId = "storage-service")
    public void consume(PdfGeneratedEvent event){
        log.info("Upload do Minio: {}",event.getFilePath());
        service.uploadPdf(event);
    }

}
