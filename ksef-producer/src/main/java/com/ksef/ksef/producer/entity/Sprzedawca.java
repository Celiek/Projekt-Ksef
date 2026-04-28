package com.ksef.ksef.producer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sprzedawca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sprzedawca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprzedawca_id;
    private String nazwa_sprzedawcy;
    private Long nip;
    private String adres;
    private String nr_konta;
}
