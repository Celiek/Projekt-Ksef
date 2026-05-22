package com.ksef.ksef.producer.entity;

import com.ksef.ksef.producer.repository.DokumentRepository;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import com.ksef.ksef.producer.repository.SprzedawcaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DokumentTest {

    @Autowired
    private DokumentRepository underTest;

    @Autowired
    private SprzedawcaRepository sprzedawcaRepository;

    @Autowired
    private NabywcaRepository nabywcaRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldSaveDokumentWithPozycje() {
        //given

        Sprzedawca sprzedawca = new Sprzedawca();
        sprzedawca.setNazwa_sprzedawcy("Firma A");
        sprzedawca = sprzedawcaRepository.save(sprzedawca);

        Nabywca nabywca = new Nabywca();
        nabywca.setNazwa_nabywcy("Klient B");
        nabywca = nabywcaRepository.save(nabywca);

        Dokument dokument = new Dokument();
        dokument.setNumer_faktury("FV/1/2024");
        dokument.setSprzedawca(sprzedawca);
        dokument.setNabywca(nabywca);
        dokument.setWystawil("Adrian Nowak");

        PozycjaDokumentu p1 = new PozycjaDokumentu();
        p1.setNazwa_uslugi("usługi informatyczne");
        p1.setCena_netto(BigDecimal.valueOf(100));
        p1.setCena_brutto(123.0);
        p1.setStawka_vat(23);

        dokument.addPozycja(p1);

        //when
        Dokument saved = underTest.save(dokument);

        //then
        assertNotNull(saved.getDokument_id());
        assertEquals(1,saved.getPozycje().size());

        PozycjaDokumentu savedP = saved.getPozycje().get(0);
        assertNotNull(savedP.getPozycja_id());
        assertEquals("usługi informatyczne",savedP.getNazwa_uslugi());
        assertEquals(saved,savedP.getDokument());

    }


}