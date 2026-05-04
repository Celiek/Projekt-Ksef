package com.ksef.ksef.producer.mapper;

import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.event.DokumentCreatedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DokumentEventMapper {

    public DokumentCreatedEvent mapToEvent(Dokument dokument){
        return new DokumentCreatedEvent(
                dokument.getDokument_id(),
                dokument.getNumer_faktury(),
                dokument.getSprzedawca().getNazwa_sprzedawcy(),
                dokument.getNabywca().getNazwa_nabywcy(),
                policzKwote(dokument)
        );

    }

    private BigDecimal policzKwote(Dokument dokument) {
        return dokument.getPozycje().stream()
                .map(p-> BigDecimal.valueOf(p.getKwota_naleznosci()))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
