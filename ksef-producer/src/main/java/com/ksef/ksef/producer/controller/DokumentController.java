package com.ksef.ksef.producer.controller;

import com.ksef.ksef.producer.DTO.DokumentRequest;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.service.DokumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dokument")
@RequiredArgsConstructor
public class DokumentController {

    private final DokumentService service;

    @PostMapping
    public ResponseEntity<Dokument> createDokument(@RequestBody DokumentRequest request){
        return ResponseEntity.ok(service.createDokument(request));
    }
}
