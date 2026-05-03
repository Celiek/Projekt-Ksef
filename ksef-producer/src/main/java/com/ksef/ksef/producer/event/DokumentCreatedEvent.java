package com.ksef.ksef.producer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DokumentCreatedEvent {
    private Long dokuemntId;
    private String numerFaktury;
    private String sprzedawca;
    private String nabywca;
    private BigDecimal kwota;
}
