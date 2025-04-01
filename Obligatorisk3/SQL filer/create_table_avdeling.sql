SET search_path TO Oblig3;

CREATE TABLE Avdeling
(
    AvdelingID    SERIAL PRIMARY KEY,
    AvdelingsNavn VARCHAR(100) UNIQUE,
    SjefID        INTEGER,
    AntallAnsatte INTEGER DEFAULT 0
);


-- Create a trigger function to update AntallAnsatte
CREATE OR REPLACE FUNCTION update_antall_ansatte()
    RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE Avdeling
        SET AntallAnsatte = AntallAnsatte + 1
        WHERE AvdelingsNavn = NEW.Avdeling;
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE Avdeling
        SET AntallAnsatte = AntallAnsatte - 1
        WHERE AvdelingsNavn = OLD.Avdeling;
    ELSIF TG_OP = 'UPDATE' AND OLD.Avdeling <> NEW.Avdeling THEN
        UPDATE Avdeling
        SET AntallAnsatte = AntallAnsatte - 1
        WHERE AvdelingsNavn = OLD.Avdeling;

        UPDATE Avdeling
        SET AntallAnsatte = AntallAnsatte + 1
        WHERE AvdelingsNavn = NEW.Avdeling;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER maintain_employee_count
    AFTER INSERT OR UPDATE OR DELETE ON Oblig3.ANSATT
    FOR EACH ROW EXECUTE FUNCTION update_antall_ansatte();
