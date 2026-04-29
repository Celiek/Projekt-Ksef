package com.ksef.ksef.producer.repository;

import com.ksef.ksef.producer.entity.Nabywca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NabywcaRepository extends JpaRepository<Nabywca,Long> {

    Optional<Nabywca> findById(Long id);
}
