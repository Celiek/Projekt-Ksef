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

CREATE TABLE dokument (
    dokument_id BIGINT PRIMARY KEY,
    numer_faktury TEXT,
    typ_faktury TEXT,
    data_wystawienia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_sprzedazy DATE,
    wystawil TEXT,
    sprzedawca_id BIGINT,
    nabywca_id BIGINT,
    CONSTRAINT fk_sprzedawca FOREIGN KEY (sprzedawca_id) REFERENCES sprzedawca(sprzedawca_id) ON DELETE RESTRICT,
    CONSTRAINT fk_nabywca FOREIGN KEY (nabywca_id) REFERENCES nabywca(nabywca_id) ON DELETE RESTRICT
);

create table pozycja_dokumentu(
    pozycja_id bigint primary key,
    id_dokument bigint not null,
    nazwa_uslugi TEXT,
    miara_towaru TEXT,
    cena_netto numeric(10,2),
    cena_brutto numeric(10,2),
    stawka_vat int,
    kwota_naleznosci numeric(10,2),
    foreign key (id_dokument) references dokument(dokument_id) on delete cascade
);

