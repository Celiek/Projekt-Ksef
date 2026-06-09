package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.DTO.*;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.entity.Nabywca;
import com.ksef.ksef.producer.entity.PozycjaDokumentu;
import com.ksef.ksef.producer.entity.Sprzedawca;
import com.ksef.ksef.producer.event.DokumentCreatedEvent;
import com.ksef.ksef.producer.mapper.DokumentEventMapper;
import com.ksef.ksef.producer.producer.DokumentEventProducer;
import com.ksef.ksef.producer.repository.DokumentRepository;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import com.ksef.ksef.producer.repository.SprzedawcaRepository;
import com.ksef.ksef.producer.request.PozycjaRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DokumentServiceTest {

    @Mock
    private DokumentRepository dokumentRepo;
    @Mock
    private NabywcaRepository nabywcaRepo;
    @Mock
    private SprzedawcaRepository sprzedawcaRepo;
    @Mock
    private DokumentEventMapper dokumentEventMapper;
    @Mock
    private DokumentEventProducer dokumentEventProducer;
    @InjectMocks
    private DokumentService dokumentService;


    @Test
    void getAllDocumentsForBuyerByNip_shouldMapResultsCorrectly() {
        Long nip = 1L;

        Object[] row = new Object[]{
               10L,
               "FV/10/05/2026",
               new Date(),
               1L,
               "Usługa 1A",
               new BigDecimal("100.00"),
               new BigDecimal("123.00"),
                new BigDecimal("23.00"),
                "Sprzedawca A",
                5L
        };

        List<Object[]> repoResult = java.util.Collections.singletonList(row);
        when(dokumentRepo.findDocumentsByBuyersNip(nip)).thenReturn(repoResult);
        //when
        List<DokumentDTO> result = dokumentService.getAllDocumentsForBuyerByNip(nip);

        //then
        assertEquals(1, result.size());
        DokumentDTO dto = result.get(0);
        assertEquals(10L, dto.getDokument_id());
        assertEquals("FV/10/05/2026",dto.getD_numer_faktury());
        assertEquals(5L, dto.getNip());
        assertEquals("Usługa 1A",dto.getNazwa_uslugi());

        verify(dokumentRepo).findDocumentsByBuyersNip(nip);
    }

    @Test
    void createDokument_shouldCreateDocumentAndSendEvent() {
        DokumentRequest req = new DokumentRequest();

        req.numerFaktury ="FA/19/05/2026";
        req.nabywcaId =10L;
        req.sprzedawcaId = 123L;

        PozycjaRequest p = new PozycjaRequest();
        p.nazwaUslugi = "UslugiInformatyczne";
        p.cenaNetto = new BigDecimal("100.00");
        p.cenaBrutto = 123.00D;
        p.stawkaVat = 23;

        req.pozycje = List.of(p);

        Nabywca nabywca = new Nabywca();
        nabywca.setNabywca_id(1L);

        Sprzedawca sprzedawca = new Sprzedawca();
        sprzedawca.setSprzedawca_id(2L);

        Dokument saved = new Dokument();
        saved.setDokument_id(10L);

        DokumentCreatedEvent event = new DokumentCreatedEvent();

        when(nabywcaRepo.findById(10L)).thenReturn(java.util.Optional.of(nabywca));
        when(sprzedawcaRepo.findById(123L)).thenReturn(java.util.Optional.of(sprzedawca));
        when(dokumentRepo.save(any(Dokument.class))).thenReturn(saved);
        when(dokumentEventMapper.mapToEvent(saved)).thenReturn(event);

        // --- WHEN ---
        //Dokument result = dokumentService.createDokument(req);

        // --- THEN ---
        //assertEquals(10L, result.getDokument_id());

        verify(nabywcaRepo).findById(10L);
        verify(sprzedawcaRepo).findById(123L);
        verify(dokumentRepo).save(any(Dokument.class));
        verify(dokumentEventMapper).mapToEvent(saved);
        verify(dokumentEventProducer).send(event);
    }

    @Test
    void patchDokument_shouldPatchDokument() {
        //given
        Long id = 1L;

        Dokument dokument = new Dokument();
        dokument.setDokument_id(id);

        PozycjaDokumentu existing = new PozycjaDokumentu();

        existing.setPozycja_id(1L);
        existing.setNazwa_uslugi("Stara usługa");
        existing.setCena_brutto(123.00);
        existing.setCena_netto(new BigDecimal("100.00"));

        dokument.setPozycje(new java.util.ArrayList<>(List.of(existing)));

        Nabywca nabywca = new Nabywca();
        nabywca.setNabywca_id(1L);

        Sprzedawca sprzedawca = new Sprzedawca();
        sprzedawca.setSprzedawca_id(123L);

        PozycjaPatchDto patch = new PozycjaPatchDto();

        patch.pozycjaId = 1L;
        patch.nazwaUslugi = "Uslugi wdrażania oprogramowania";
        patch.cenaBrutto = 200.00;
        patch.cenaNetto = new BigDecimal("246.00");

        DokumentPatchRequest dok =
                new DokumentPatchRequest();

        dok.numerFaktury = "FA/19/05/2026";
        dok.nabywcaId = 1L;
        dok.sprzedawcaId = 123L;
        dok.pozycje = List.of(patch);

        when(dokumentRepo.findById(id)).thenReturn(Optional.of(dokument));

        when(nabywcaRepo.findById(1L)).thenReturn(Optional.of(nabywca));

        when(sprzedawcaRepo.findById(123L)).thenReturn(Optional.of(sprzedawca));

        when(dokumentRepo.save(any(Dokument.class))).thenReturn(dokument);
        //when

        DokumentResponseDTO result = dokumentService.patchDokument(id, dok);

        //then
        assertEquals("Uslugi wdrażania oprogramowania",existing.getNazwa_uslugi());
        assertEquals(new BigDecimal("246.00"),existing.getCena_netto());
        assertEquals(200.00, existing.getCena_brutto());

        verify(dokumentRepo).save(any(Dokument.class));
        verify(dokumentRepo).findById(id);
    }
}