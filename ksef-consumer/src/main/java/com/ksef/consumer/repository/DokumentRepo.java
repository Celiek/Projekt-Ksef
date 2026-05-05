package com.ksef.consumer.repository;

import com.ksef.consumer.entity.Dokument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DokumentRepo extends JpaRepository<Dokument, Long> {

}
