package com.ksef.storage_service.event;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PdfGeneratedEvent {
    private Long dokumentId;
    private String numerFaktury;
    private String filepath;
    private String ownerId;
}
