package com.ksef.pdf_service.event;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DokumentCreatedEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String sprzedawca;
    private String nabywca;
    private BigDecimal kwota;
}
