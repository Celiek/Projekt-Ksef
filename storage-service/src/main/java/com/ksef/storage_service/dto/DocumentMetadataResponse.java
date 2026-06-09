package com.ksef.storage_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMetadataResponse {
    private Long dokumentId;
    private String numerFaktury;
    private String bucketName;
    private String objectName;
    private String mimeType;
    private Long fileSize;
    private String ownerId;
}
