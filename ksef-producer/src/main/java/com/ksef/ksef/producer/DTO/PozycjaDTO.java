package com.ksef.ksef.producer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PozycjaDTO {
    private String nazwaUslugi;
    private BigDecimal cenaNetto;
    private Double cenaBrutto;
    private int stawkaVat;
}
