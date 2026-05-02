package com.ksef.ksef.producer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DokumentDTO {
    private Long dokument_id;
    private String d_numer_faktury;
    private Date data_wystawienia;
    private Long pozycja_id;
    private String nazwa_uslugi;
    private BigDecimal cena_netto;
    private BigDecimal cena_brutto;
    private BigDecimal kwota_naleznosci;
    private String nazwa_nabywcy;
    private Long nip;
}
