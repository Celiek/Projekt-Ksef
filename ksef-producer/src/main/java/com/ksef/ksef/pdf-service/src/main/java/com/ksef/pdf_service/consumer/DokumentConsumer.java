package com.ksef.pdf_service.consumer;

import com.ksef.pdf_service.event.DokumentCreatedEvent;
import com.ksef.pdf_service.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DokumentConsumer {
    private final PdfService pdfService;

    @KafkaListener(
            topics = "dokument-created",
            groupId = "pdf-service"
    )
    public void consume(DokumentCreatedEvent event){
        log.info("Otrzymano event: {}",event.getNumerFaktury());
        pdfService.generatePdf(event);
    }

}
