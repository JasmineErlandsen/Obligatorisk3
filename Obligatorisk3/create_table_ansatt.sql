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
    --  FOREIGN KEY (Stilling) REFERENCES Stilling (Stilling),
    Manedslonn      DECIMAL(5, 2),
    Avdeling        VARCHAR(100)           NOT NULL,
    --  FOREIGN KEY Avdeling REFERENCES Avdeling (Avdelingsnavn),
    ErSjef          BOOLEAN
);

-- CREATE TABLE Avdeling (
-- AvdelingID SERIAL PRIMARY KEY ,
-- AvdelingsNavn VARCHAR(100) PRIMARY KEY,
-- Sjef VARCHAR(151),
--  FOREIGN KEY (Sjef) REFERENCES no.hvl.dat107.entity.Ansatt (Fornavn, Etternavn),
--AntallAnsatte INTEGER
-- );



