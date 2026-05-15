CREATE TABLE dokument_file (
    id BIGSERIAL PRIMARY KEY,
    dokument_id BIGINT NOT NULL,
    numer_faktury TEXT,
    bucket_name TEXT,
    object_name TEXT,
    file_url TEXT,
    file_size BIGINT,
    mime_type TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);