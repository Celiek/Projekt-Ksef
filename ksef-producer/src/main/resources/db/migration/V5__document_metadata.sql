create table document_metadata(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dokument_id BIGINT,
    numer_faktury TEXT,
    bucket_name TEXT,
    object_name TEXT,
    mime_type TEXT,
    file_size BIGINT,
    created_at TIMESTAMP,
    status TEXT);