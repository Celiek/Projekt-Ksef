package com.ksef.ksef.producer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dokument")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dokument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dokument_id;

    private String numer_faktury;
    private String typ_faktury;

    private LocalDate data_wystawienia;
    private LocalDate data_sprzedazy;

    private String wystawil;

    @ManyToOne
    @JoinColumn(name = "sprzedawca_id")
    private Sprzedawca sprzedawca;
    @ManyToOne
    @JoinColumn(name = "nabywca_id")
    private Nabywca nabywca;

}
