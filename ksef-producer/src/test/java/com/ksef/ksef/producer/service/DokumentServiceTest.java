package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.repository.DokumentRepository;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import com.ksef.ksef.producer.repository.SprzedawcaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DokumentServiceTest {

    @Mock
    private DokumentRepository dokumentRepo;
    private AutoCloseable autoCloseable;
    private SprzedawcaRepository sprzedawcaRepo;
    private NabywcaRepository nabywcaRepo;

    @Test
    void getAllDocumentsForBuyerByNip() {
    }

    @Test
    void createDokument() {
    }

    @Test
    void patchDokument() {
    }
}