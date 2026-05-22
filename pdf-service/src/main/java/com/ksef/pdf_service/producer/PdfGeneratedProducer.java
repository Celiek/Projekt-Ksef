package com.ksef.pdf_service.producer;

import com.ksef.pdf_service.event.PdfGeneratedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfGeneratedProducer {
    private final KafkaTemplate<String, PdfGeneratedEvent> kafkaTemplate;

    public void send(PdfGeneratedEvent event){
        kafkaTemplate.send(
                "pdf.generated",
                event
        );
    }
}
