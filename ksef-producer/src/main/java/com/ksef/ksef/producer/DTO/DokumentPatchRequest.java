package com.ksef.ksef.producer.DTO;

import java.time.LocalDate;
import java.util.List;

public class DokumentPatchRequest {
    public String numerFaktury;
    public String typFaktury;
    public Long nabywcaId;
    public Long sprzedawcaId;
    public List<PozycjaPatchDto> pozycje;
}
