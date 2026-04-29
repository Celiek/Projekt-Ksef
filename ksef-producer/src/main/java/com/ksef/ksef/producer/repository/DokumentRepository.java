package com.ksef.ksef.producer.repository;

import com.ksef.ksef.producer.DTO.DokumentDTO;
import com.ksef.ksef.producer.entity.Dokument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface DokumentRepository extends JpaRepository< Dokument, Long> {

    @Transactional
    @Query(value = "SELECT d.dokument_id, d.numer_faktury d.data_wystawienia," +
            "p.pozycja_id," +
            "p.nazwa_uslugi," +
            "p.cena_netto, p.cena_brutto,p.kwota_naleznosci" +
            "n.nazwa_nabywcy, n.nip" +
            "from dokument d" +
            "join nabywca n on d.nabywca_id = n.nabywca_id" +
            "join pozycja_dokumentu p on p.id_dokument = d.dokument_id" +
            "WHERE n.nip = :nip;", nativeQuery = true)
    List<DokumentDTO> findDocumentsByBuyersNip(@Param("nip") Long nip);

    @Transactional
    @Query
    void addDokument();
}
