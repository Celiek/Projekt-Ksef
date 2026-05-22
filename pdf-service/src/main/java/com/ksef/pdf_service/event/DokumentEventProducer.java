package com.ksef.pdf_service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DokumentEventProducer {
    private final KafkaTemplate<String, DokumentCreatedEvent> kafkaTemplate;

    public void send(DokumentCreatedEvent event) {

        kafkaTemplate.send("dokument-created", event);
    }
}
