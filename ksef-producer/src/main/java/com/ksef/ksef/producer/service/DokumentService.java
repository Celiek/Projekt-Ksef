package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.DTO.DokumentDTO;
import com.ksef.ksef.producer.DTO.DokumentRequest;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.entity.Nabywca;
import com.ksef.ksef.producer.entity.PozycjaDokumentu;
import com.ksef.ksef.producer.entity.Sprzedawca;
import com.ksef.ksef.producer.repository.DokumentRepository;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import com.ksef.ksef.producer.repository.SprzedawcaRepository;
import com.ksef.ksef.producer.request.PozycjaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DokumentService {

    private final DokumentRepository dokumentRepo;
    private final NabywcaRepository nabywcaRepo;
    private final SprzedawcaRepository sprzedawcaRepo;

    // zwraca wszystkie faktury zakupowe po nipie dla nabywcy
    public List<DokumentDTO> getAllDocumentsForBuyerByNip(Long nip){

           List<DokumentDTO> dokumenty =  dokumentRepo.findDocumentsByBuyersNip(nip)
                   .stream()
                   .map(r -> new DokumentDTO(
                           (Long) r[0],
                           (String) r[1],
                           (Date) r[2],
                           (Long) r[3],
                           (String) r[4],
                           (BigDecimal) r[5],
                           (BigDecimal) r[6],
                           (BigDecimal) r[7],
                           (String) r[8],
                           (Long) r[9]
                   )).toList();

           return dokumenty;
    }

    // zapisuje faktury do bazy danych
    public Dokument createDokument(DokumentRequest request){
        Dokument dokument = new Dokument();

        dokument.setNumer_faktury(request.numerFaktury);

        Nabywca nabywca = nabywcaRepo.findById(request.nabywcaId)
                .orElseThrow(() -> new RuntimeException("Nabywca nie znaleziony"));

        Sprzedawca sprzedawca = sprzedawcaRepo.findById(request.sprzedawcaId)
                .orElseThrow(() -> new RuntimeException("Sprzedawca nie znaleziony"));

        dokument.setNabywca(nabywca);
        dokument.setSprzedawca(sprzedawca);

        for(PozycjaRequest p : request.pozycje){
            PozycjaDokumentu pozycja = new PozycjaDokumentu();
            pozycja.setNazwa_uslugi(p.nazwaUslugi);
            pozycja.setCena_brutto(p.cenaBrutto);
            pozycja.setCena_netto(p.cenaNetto);
            pozycja.setStawka_vat(p.stawkaVat);

            dokument.addPozycja(pozycja);
        }
        return dokumentRepo.save(dokument);
    }


}
