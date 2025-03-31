DROP SCHEMA IF EXISTS Oblig3 CASCADE;

CREATE SCHEMA Oblig3;
SET search_path TO Oblig3;

CREATE TABLE ANSATT
(
    AnsattID        SERIAL PRIMARY KEY     NOT NULL,
    Brukernavn      VARCHAR(4)             NOT NULL UNIQUE,
    Fornavn         VARCHAR(50)            NOT NULL,
    Etternavn       VARCHAR(100)           NOT NULL,
    AnsettelsesDato DATE,
    Stilling        VARCHAR(100),
    Manedslonn      DECIMAL(5, 2),
    Avdeling        VARCHAR(100)           NOT NULL,
    ErSjef          BOOLEAN
);



