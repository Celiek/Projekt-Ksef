package com.ksef.storage_service.consumer;

import com.ksef.storage_service.event.PdfGeneratedEvent;
import com.ksef.storage_service.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PdfGeneratedConsumer {
    private final StorageService storageService;
    @KafkaListener(
            topics = "pdf-generated",
            groupId = "storage-service"
    )
    public void consume(PdfGeneratedEvent event) {
        log.info("Odebrano PDF: {}", event.getNumerFaktury());
        storageService.uploadPdf(event);
    }
}
