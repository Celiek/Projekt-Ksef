package com.ksef.pdf_service.service;

import com.ksef.pdf_service.event.DokumentCreatedEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PdfService {
    public void generatePdf(DokumentCreatedEvent event){
        String fileName =
                "pdf/" + event.getNumerFaktury() + ".pdf";

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

        } catch (Exception e) {

            log.error(
                    "Błąd generowania PDF",
                    e
            );
        }
    }
}
