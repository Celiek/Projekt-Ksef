package com.ksef.consumer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pozycja_dokumentu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PozycjaDokumentu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pozycja_id;
    private String nazwa_uslugi;
    private String miara_towaru;
    private BigDecimal cena_netto;
    private Double cena_brutto;
    private int stawka_vat;
    private Double kwota_naleznosci;

    @ManyToOne
    @JoinColumn(name = "id_dokument", nullable = false)
    private Dokument dokument;

}

