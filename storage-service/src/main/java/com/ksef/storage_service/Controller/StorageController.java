package com.ksef.storage_service.Controller;

import com.ksef.storage_service.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Slf4j
public class StorageController {
    private final StorageService service;

    @GetMapping("/pdf/{objectName}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long dokumentId,
                                              JwtAuthenticationToken token) throws IOException {
        String ownerID = token.getToken().getSubject();

        InputStream inputStream = service.downloadPdfByDocumentID(dokumentId,ownerID);

        byte[] pdfBytes = inputStream.readAllBytes();


        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"document"+dokumentId+".pdf\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
