package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.DTO.*;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.entity.Nabywca;
import com.ksef.ksef.producer.entity.PozycjaDokumentu;
import com.ksef.ksef.producer.entity.Sprzedawca;
import com.ksef.ksef.producer.event.DokumentCreatedEvent;
import com.ksef.ksef.producer.mapper.DokumentEventMapper;
import com.ksef.ksef.producer.mapper.DokumentMapper;
import com.ksef.ksef.producer.producer.DokumentEventProducer;
import com.ksef.ksef.producer.repository.DokumentRepository;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import com.ksef.ksef.producer.repository.SprzedawcaRepository;
import com.ksef.ksef.producer.request.PozycjaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DokumentService {

    private final DokumentRepository dokumentRepo;
    private final NabywcaRepository nabywcaRepo;
    private final SprzedawcaRepository sprzedawcaRepo;
    private final DokumentEventMapper dokumentEventMapper;
    private final DokumentEventProducer dokumentEventProducer;

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
        dokument.setOwnerId(request.ownerId);

        for(PozycjaRequest p : request.pozycje){
            PozycjaDokumentu pozycja = new PozycjaDokumentu();
            pozycja.setNazwa_uslugi(p.nazwaUslugi);
            pozycja.setCena_brutto(p.cenaBrutto);
            pozycja.setCena_netto(p.cenaNetto);
            pozycja.setStawka_vat(p.stawkaVat);

            dokument.addPozycja(pozycja);
        }

        Dokument saved = dokumentRepo.save(dokument);

        DokumentCreatedEvent event =
                dokumentEventMapper.mapToEvent(saved);

        dokumentEventProducer.send(event);

        return saved;
    }

    @Transactional
    public DokumentResponseDTO patchDokument(Long id, DokumentPatchRequest request){
        Dokument dokument = dokumentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Dokument nie istnieje"));

        if (request.numerFaktury != null) {
            dokument.setNumer_faktury(
                    request.numerFaktury);
        }

        if (request.typFaktury != null) {
            dokument.setTyp_faktury(
                    request.typFaktury);
        }

        if (request.nabywcaId != null) {
            dokument.setNabywca(
                    nabywcaRepo.findById(
                                    request.nabywcaId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Nabywca nie istnieje"))
            );
        }

        if (request.sprzedawcaId != null) {
            dokument.setSprzedawca(
                    sprzedawcaRepo.findById(
                                    request.sprzedawcaId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Sprzedawca nie istnieje"))
            );
        }

        if (request.pozycje != null) {

            if (dokument.getPozycje() == null) {
                dokument.setPozycje(
                        new ArrayList<>());
            }

            for (PozycjaPatchDto p
                    : request.pozycje) {

                if (p.pozycjaId != null) {

                    PozycjaDokumentu existing =
                            dokument.getPozycje()
                                    .stream()
                                    .filter(x ->
                                            x.getPozycja_id()
                                                    .equals(
                                                            p.pozycjaId))
                                    .findFirst()
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Pozycja nie znaleziona"));

                    if (p.nazwaUslugi != null) {
                        existing.setNazwa_uslugi(
                                p.nazwaUslugi);
                    }

                    if (p.cenaNetto != null) {
                        existing.setCena_netto(
                                p.cenaNetto);
                    }

                    if (p.cenaBrutto != null) {
                        existing.setCena_brutto(
                                p.cenaBrutto);
                    }

                } else {

                    PozycjaDokumentu nowa =
                            new PozycjaDokumentu();

                    nowa.setNazwa_uslugi(
                            p.nazwaUslugi);

                    nowa.setCena_netto(
                            p.cenaNetto);

                    nowa.setCena_brutto(
                            p.cenaBrutto);

                    dokument.addPozycja(nowa);
                }
            }
        }

        Dokument saved = dokumentRepo.save(dokument);
        DokumentMapper dokumentMapper = new DokumentMapper();
        return dokumentMapper.toDto(saved);
    }

}
