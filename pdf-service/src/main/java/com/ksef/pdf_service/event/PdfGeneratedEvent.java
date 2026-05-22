package com.ksef.pdf_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfGeneratedEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String filepath;
}
