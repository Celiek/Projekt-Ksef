create table sprzedawca(
    sprzedawca_id bigint PRIMARY KEY,
    nazwa_sprzedawcy TEXT,
    nip bigint,
    adres TEXT,
    nr_konta TEXT
);

create table nabywca(
    nabywca_id bigint primary key,
    nazwa_nabywcy TEXT,
    nip bigint,
    adres TEXT
);

CREATE TABLE dokument(
    dokument_id bigint PRIMARY KEY,
    numer_faktury TEXT,
    faktura_hash TEXT not null,
    data_wystawienia timestapn default CURRENT_TIMESTAMP,
    data_sprzedazy date,
    nazwa_uslugi TEXT,
    miara_towaru TEXT,
    cena_netto numeric(10,2),
    cena_brutto numeric(10,2),
    stawka_vat int,
    kwota_naleznosci numeric(10,2),
    wystawil text,
    id_sprzedawca bigint,
    id_nabywca bigint,
    CONSTRAINT fk_sprzedawca_id foreign key (sprzedawca_id) references sprzedawca(sprzedawca_id) ON DELETE RESTRICT,
    CONSTRAINT fk_nabywca_id foreign key (nabywca_id) references nabywca(nabywca_id) ON DELETE RESTRICT
);

