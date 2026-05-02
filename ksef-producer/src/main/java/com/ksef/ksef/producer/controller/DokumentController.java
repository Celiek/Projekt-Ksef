package com.ksef.ksef.producer.controller;

import com.ksef.ksef.producer.DTO.DokumentRequest;
import com.ksef.ksef.producer.DTO.DokumentResponseDTO;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.mapper.DokumentMapper;
import com.ksef.ksef.producer.service.DokumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dokument")
@RequiredArgsConstructor
public class DokumentController {

    private final DokumentService service;
    private final DokumentMapper dokumentMapper;

    @PostMapping
    public DokumentResponseDTO createDokument(@RequestBody DokumentRequest request){
        Dokument dokument = service.createDokument(request);
        return dokumentMapper.toDto(dokument);
    }
}
