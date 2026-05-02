package com.ksef.ksef.producer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DokumentResponseDTO {
    private Long dokument_id;
    private String numer_faktury;
    private String nazwa_sprzedawcy;
    private String nazwaNabywcy;
    private List<PozycjaDTO> pozycje;
}
