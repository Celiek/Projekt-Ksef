package com.ksef.ksef.producer.controller;

import com.ksef.ksef.producer.DTO.DokumentPatchRequest;
import com.ksef.ksef.producer.DTO.DokumentRequest;
import com.ksef.ksef.producer.DTO.DokumentResponseDTO;
import com.ksef.ksef.producer.entity.Dokument;
import com.ksef.ksef.producer.event.DokumentCreatedEvent;
import com.ksef.ksef.producer.mapper.DokumentEventMapper;
import com.ksef.ksef.producer.mapper.DokumentMapper;
import com.ksef.ksef.producer.producer.DokumentEventProducer;
import com.ksef.ksef.producer.service.DokumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dokument")
@RequiredArgsConstructor
public class DokumentController {

    private final DokumentService service;
    private final DokumentMapper dokumentMapper;
    private final DokumentEventMapper dokumentEventMapper;
    private final DokumentEventProducer producer;

    @PostMapping
    public DokumentResponseDTO createDokument(@RequestBody DokumentRequest request){
        Dokument dokument = service.createDokument(request);

        DokumentCreatedEvent event = dokumentEventMapper.mapToEvent(dokument);
        producer.send(event);
        return dokumentMapper.toDto(dokument);
    }

    @PatchMapping("/{id}")
    public DokumentResponseDTO patchDokument(
            @PathVariable Long id,
            @RequestBody DokumentPatchRequest request){
        return service.patchDokument(id,request);
    }
}
