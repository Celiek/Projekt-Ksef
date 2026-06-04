package com.ksef.storage_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfUploadEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String bucketName;
    private String objectName;
    private String mimeType;
    private Long fileSize;
}
