package com.ksef.metadata_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfUploadEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String BucketName;
    private String object_name;
    private Long filename;
    private String mimeType;

}
