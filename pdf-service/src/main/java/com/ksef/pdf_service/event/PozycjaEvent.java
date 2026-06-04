package com.ksef.pdf_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PozycjaEvent {
    private String nazwaUslugi;
    private String miaraTowaru;
    private BigDecimal cenaNetto;
    private BigDecimal cenaBrutto;
    private int vat;
    private BigDecimal kwotaNaleznosci;
}
