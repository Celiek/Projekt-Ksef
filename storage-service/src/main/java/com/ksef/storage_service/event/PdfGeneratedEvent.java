package com.ksef.storage_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfGeneratedEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String filePath;
}
