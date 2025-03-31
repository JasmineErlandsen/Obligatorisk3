SET search_path TO oblig3;

ALTER TABLE Ansatt
    ADD CONSTRAINT fk_avdeling
        FOREIGN KEY (Avdeling) REFERENCES Avdeling(AvdelingsNavn);


-- Lager en funksjon for å sørge for at det er en ansatt igjen
-- og sørger for at siste ansatt alltid er Sjef
CREATE OR REPLACE FUNCTION check_last_employee()
    RETURNS TRIGGER AS $$
BEGIN

    IF TG_OP = 'DELETE' THEN
        -- Sjekker om dette er siste ansatt
        IF (SELECT COUNT(*) FROM Ansatt WHERE Avdeling = OLD.Avdeling) = 1 THEN
            RAISE EXCEPTION 'Kan ikke slette siste ansatt i avdelingen';
        END IF;

        -- Ved sletting av nåværende Sjef
        IF OLD.ErSjef = true THEN
            UPDATE Ansatt
            SET ErSjef = true
            WHERE Avdeling = OLD.Avdeling
                LIMIT 1;
        END IF;
    END IF;

    -- Ved endring av avdeling
    IF TG_OP = 'UPDATE' AND OLD.Avdeling <> NEW.Avdeling THEN
        -- Sjekk om dette er siste ansatt i avdelingen
        IF (SELECT COUNT(*) FROM Ansatt WHERE Avdeling = OLD.Avdeling) = 1 THEN
            RAISE EXCEPTION 'Kan ikke flytte siste ansatt i avdelingen';
        END IF;

        -- Når Sjef blir flyttet
        IF OLD.ErSjef = true THEN
            UPDATE Ansatt
            SET ErSjef = true
            WHERE Avdeling = OLD.Avdeling
                LIMIT 1;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_last_employee_trigger
    BEFORE DELETE OR UPDATE ON Ansatt
    FOR EACH ROW EXECUTE FUNCTION check_last_employee();


-- Lager funksjon som sørger fo at det alltid er en ansatt igjenfunction to ensure at least one employee per department
CREATE OR REPLACE FUNCTION ensure_department_has_employee()
    RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Ansatt WHERE Avdeling = NEW.AvdelingsNavn) THEN
        RAISE EXCEPTION 'Department must have at least one employee';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ensure_department_has_employee_trigger
    BEFORE INSERT ON Avdeling
    FOR EACH ROW EXECUTE FUNCTION ensure_department_has_employee();