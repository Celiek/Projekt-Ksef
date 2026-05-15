package com.ksef.metadata_service.controller;

import com.ksef.metadata_service.entity.DokumentMetadata;
import com.ksef.metadata_service.service.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metadata")
@RequiredArgsConstructor
public class MetadataController {
    private final MetadataService service;

    @GetMapping("/document/{id}")
    public DokumentMetadata getDokumentId(@PathVariable Long id) {
        return service.findByDokumentId(id);
    }

    @GetMapping("/invoice/{number}")
    public DokumentMetadata getByInvoice(@PathVariable String number){
        return service.findByNumerFaktury(number);
    }

}
