package com.ksef.pdf_service.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.ksef.pdf_service.event.DokumentCreatedEvent;
import com.ksef.pdf_service.event.PdfGeneratedEvent;
import com.ksef.pdf_service.producer.PdfGeneratedProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {

    private final PdfGeneratedProducer producer;


    public void generatePdf(DokumentCreatedEvent event){
        String pdfDir = "/tmp/ksef-pdf";
        new File(pdfDir).mkdirs();

        String fileName =
                pdfDir + "/" + UUID.randomUUID() + ".pdf";
        log.info("Tworzenie PDF: {}", fileName);

        try {

            PdfWriter writer =
                    new PdfWriter(fileName);

            PdfDocument pdf =
                    new PdfDocument(writer);

            Document document =
                    new Document(pdf);

            document.add(
                    new Paragraph(
                            "Faktura: "
                                    + event.getNumerFaktury()
                    )
            );

            document.add(
                    new Paragraph(
                            "Sprzedawca: "
                                    + event.getSprzedawca()
                    )
            );

            document.add(
                    new Paragraph(
                            "Nabywca: "
                                    + event.getNabywca()
                    )
            );

            document.add(
                    new Paragraph(
                            "Kwota: "
                                    + event.getKwota()
                    )
            );

            document.close();

            log.info("PDF wygenerowany");

            producer.send(
                    new PdfGeneratedEvent(
                            event.getDokumentId(),
                            event.getNumerFaktury(),
                            fileName
                    )
            );
            log.info("PDF wygenerowany i event wysłany: {}", fileName);

        } catch (Exception e) {

            log.error(
                    "Błąd generowania PDF",
                    e
            );
        }
    }
}
