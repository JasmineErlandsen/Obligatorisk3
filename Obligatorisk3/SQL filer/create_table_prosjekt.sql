SET search_path TO Oblig3;

CREATE TABLE PROSJEKT
(
  ProsjektID SERIAL PRIMARY KEY,
  ProsjektNavn VARCHAR(100) NOT NULL,
  ProsjektBeskrivelse TEXT
);