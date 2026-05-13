package com.ksef.storage_service.consumer;

import com.ksef.storage_service.event.PdfGeneratedEvent;
import com.ksef.storage_service.service.MinioService;
import com.ksef.storage_service.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PdfGeneratedConsumer {
    private final MinioService service;
    private final StorageService storageService;

    @KafkaListener(topics = "pdf-generated",groupId = "storage-service")
    public void consume(PdfGeneratedEvent event){
        log.info(
                "Odebrano PDF: {}",
                event.getNumerFaktury()
        );

        storageService.uploadPdf(
                event.getFilePath(),
                event.getNumerFaktury()
        );
    }

}
