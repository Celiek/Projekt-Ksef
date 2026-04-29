package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.repository.DokumentRepository;
import org.springframework.stereotype.Service;
import com.ksef.ksef.producer.DTO.DokumentDTO;

import java.util.List;

@Service
public class DokumentService {

    private final DokumentRepository dokumentRepo;


    public DokumentService(DokumentRepository dokumentRepo) {
        this.dokumentRepo = dokumentRepo;
    }

    // zwraca wszystkie faktury zakupowe po nipie dla nabywcy
    public List<DokumentDTO> getAllDocumentsForBuyerByNip(Long nip){
           List<DokumentDTO> dokumenty =  dokumentRepo.findDocumentsByBuyersNip(nip);
           return dokumenty;
    }



}
