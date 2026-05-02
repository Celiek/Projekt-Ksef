package com.ksef.ksef.producer.mapper;

import com.ksef.ksef.producer.DTO.DokumentResponseDTO;
import com.ksef.ksef.producer.DTO.PozycjaDTO;
import com.ksef.ksef.producer.entity.Dokument;
import org.springframework.stereotype.Component;

@Component
public class DokumentMapper {
    public DokumentResponseDTO toDto(Dokument dokument){
        DokumentResponseDTO dto = new DokumentResponseDTO();

        dto.setDokument_id(dokument.getDokument_id());
        dto.setNumer_faktury(dokument.getNumer_faktury());
        dto.setNazwa_sprzedawcy(String.valueOf(dokument.getSprzedawca()));
        dto.setNazwaNabywcy(String.valueOf(dokument.getNabywca()));

        dto.setPozycje(
                dokument.getPozycje().stream()
                        .map(p -> new PozycjaDTO(
                                p.getNazwa_uslugi(),
                                p.getCena_netto(),
                                p.getCena_brutto(),
                                p.getStawka_vat()
                        ))
                        .toList()
        );

        return dto;
    }
}
