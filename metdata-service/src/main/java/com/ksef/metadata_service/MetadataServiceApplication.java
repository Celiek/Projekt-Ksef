package com.ksef.metadata_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetadataServiceApplication {
    // mikroserwis do przechowywania informacji o położeniu plików pdf na minio
	public static void main(String[] args) {
		SpringApplication.run(MetadataServiceApplication.class, args);
	}

}
