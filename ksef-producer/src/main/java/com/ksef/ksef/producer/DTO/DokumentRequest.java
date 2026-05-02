package com.ksef.ksef.producer.DTO;

import com.ksef.ksef.producer.request.PozycjaRequest;

import java.util.List;

public class DokumentRequest {
    public String numerFaktury;
    public Long nabywcaId;
    public Long sprzedawcaId;
    public List<PozycjaRequest> pozycje;
}
