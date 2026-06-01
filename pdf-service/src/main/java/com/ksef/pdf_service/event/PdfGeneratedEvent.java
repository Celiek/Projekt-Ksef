package com.ksef.pdf_service.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PdfGeneratedEvent {
    //dodać resztę pól do faktury
    private Long dokumentId;
    private String numerFaktury;
    private String filepath;
}
