package com.ksef.ksef.producer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "nabywca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nabywca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nabywca_id;

    private String nazwa_nabywcy;
    private Long nip;
    private String adres;

    @OneToMany(mappedBy = "sprzedawca")
    private List<Dokument> dokument;

}
