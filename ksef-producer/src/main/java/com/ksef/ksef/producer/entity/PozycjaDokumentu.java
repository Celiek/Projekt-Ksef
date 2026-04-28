package com.ksef.ksef.producer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long dokument_id;
    private String nazwa_uslugi;
    private String miara_towaru;
    private Double cena_netto;
    private Double cena_brutto;
    private int stawka_vat;
    private Double kwota_naleznosci;

    @ManyToOne
    @JoinColumn(name = "dokument_id", nullable = false)
    private Dokument dokument;

}
