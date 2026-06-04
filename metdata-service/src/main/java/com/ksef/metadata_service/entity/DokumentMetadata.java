package com.ksef.metadata_service.entity;


import com.ksef.metadata_service.enumerator.StorageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DokumentMetadata {
    // TODO
    // 1 - nie zapisuje nazwy bucketa, object_name, mime_type ani file_size
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dokumentId;

    private String numerFaktury;

    private String bucketName;

    private String objectName;

    private String mimeType;

    private Long fileSize;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private StorageStatus status;
}
