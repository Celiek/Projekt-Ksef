package com.ksef.metadata_service.consumer;

import com.ksef.metadata_service.event.PdfUploadEvent;
import com.ksef.metadata_service.service.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final MetadataService service;

    @KafkaListener(
            topics = "pdf-generated",
            groupId = "metadata-service"
    )
    public void consume(PdfUploadEvent event){
        log.info("Zapisana metadata {}",event.getNumerFaktury());
        service.save(event);
    }
}
