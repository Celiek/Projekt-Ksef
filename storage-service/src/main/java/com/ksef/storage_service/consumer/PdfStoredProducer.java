package com.ksef.storage_service.consumer;

import com.ksef.storage_service.event.PdfUploadEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfStoredProducer {

    private final KafkaTemplate<String, PdfUploadEvent> kafkaTemplate;

    public void send(PdfUploadEvent event){
        kafkaTemplate.send("pdf-stored",event);
    }

}
