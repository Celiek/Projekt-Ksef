package com.ksef.pdf_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DokumentCreatedEvent {

    private Long dokumentId;
    private String numerFaktury;
    private String sprzedawca;
    private String nabywca;
    private LocalDate dataWystawienia;
    private LocalDate dataSprzedazy;
    private BigDecimal kwotaNetto;
    private BigDecimal kwotaBrutto;
    private int kwotaVat;
    private List<PozycjaEvent> pozycje;
}
