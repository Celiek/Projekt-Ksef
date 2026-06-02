package com.ksef.metadata_service.Exception;

public class MetadataNotFoundAdvice extends RuntimeException{
    public MetadataNotFoundAdvice(String message){
        super(message);
    }
}
