package com.ksef.ksef.producer.producer;

import com.ksef.ksef.producer.event.DokumentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DokumentEventProducer {
    private final KafkaTemplate<String, DokumentCreatedEvent> kafkaTemplate;

    public void send(DokumentCreatedEvent event){
        kafkaTemplate.send("dokument-created",event);
    }

}
