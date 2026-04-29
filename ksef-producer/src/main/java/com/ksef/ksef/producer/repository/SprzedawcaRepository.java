package com.ksef.ksef.producer.repository;

import com.ksef.ksef.producer.entity.Sprzedawca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprzedawcaRepository extends JpaRepository< Sprzedawca, Long> {

    Optional<Sprzedawca> findById(Long id);
}
