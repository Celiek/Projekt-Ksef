package com.ksef.ksef.producer.service;

import com.ksef.ksef.producer.entity.Nabywca;
import com.ksef.ksef.producer.repository.NabywcaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NabywcaService {

    private final NabywcaRepository repo;

    public Nabywca createNabywca(String nazwa, Long nip, String adres){
        Nabywca n = new Nabywca();
        n.setNazwa_nabywcy(nazwa);
        n.setNip(nip);
        n.setAdres(adres);

        return repo.save(n);
    }

}
