package com.ksef.pdf_service.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.ksef.pdf_service.event.DokumentCreatedEvent;
import com.ksef.pdf_service.event.PdfGeneratedEvent;
import com.ksef.pdf_service.event.PozycjaEvent;
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


    private static final String PDF_DIR = "/tmp/ksef-pdf";
    private final PdfGeneratedProducer producer;

    public void generatePdf(DokumentCreatedEvent event){

        new File(PDF_DIR).mkdirs();
        String filePath =
                PDF_DIR + "/" + UUID.randomUUID() + ".pdf";


        log.info("Tworzenie PDF: {}", filePath);

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("FAKTURA"));
            document.add(new Paragraph("Numer faktury: " + event.getNumerFaktury()));
            document.add(new Paragraph("Data wystawienia: " + event.getDataWystawienia()));
            document.add(new Paragraph("Data sprzedaży: " + event.getDataSprzedazy()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Sprzedawca: " + event.getSprzedawca()));
            document.add(new Paragraph("Nabywca: " + event.getNabywca()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Pozycje:"));

            if (event.getPozycje() != null) {
                for (PozycjaEvent p : event.getPozycje()) {
                    document.add(new Paragraph(
                            p.getNazwaUslugi()
                                    + " | netto: " + p.getCenaNetto()
                                    + " | VAT: " + p.getVat()
                                    + "%"
                                    + " | brutto: " + p.getCenaBrutto()
                    ));
                }
            }

//            document.add(new Paragraph(" "));
//            document.add(new Paragraph("Razem: " + event.getKwota()));

            document.close();

            log.info("Wysyłam pdf-generated dla dokumentId={}, numer={}",
                    event.getDokumentId(),
                    event.getNumerFaktury()
            );

            producer.send(
                    new PdfGeneratedEvent(
                            event.getDokumentId(),
                            event.getNumerFaktury(),
                            filePath
                    )
            );

            log.info("PDF wygenerowany i event wysłany: {}", filePath);

        } catch (Exception e) {
            log.error("Błąd generowania PDF", e);
        }
    }
}
