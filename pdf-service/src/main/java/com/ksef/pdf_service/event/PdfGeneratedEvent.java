package com.ksef.pdf_service.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PdfGeneratedEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String filepath;
    private String ownerId;
}
