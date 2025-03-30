package no.hvl.dat107.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(schema = "Oblig3")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AnsattID;
    private String Brukernavn;
    private String Fornavn;
    private String Etternavn;
    private Date AnsettelsesDato;
    private String Stilling;
    private double Manedslonn;
    private String Avdeling;
    private boolean erSjef;


    @OneToMany(mappedBy="Ansatt")
    private List<Prosjektdeltagelse> deltagelser;

    public void skrivUt(String innrykk) {
        System.out.printf("%sAnsatt nr %d: %s %s", innrykk, AnsattID, Fornavn, Etternavn);
    }

    public void skrivUtMedProsjekter() {
        System.out.println();
        skrivUt("");
        deltagelser.forEach(p -> p.skrivUt("\n   "));
    }

    public void leggTilProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.add(prosjektdeltagelse);
    }

    public void fjernProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.remove(prosjektdeltagelse);
    }

    public int getAnsattID() {

        return AnsattID;
    }

    public void setAnsattID(int ansattID) {

        AnsattID = ansattID;
    }

    public String getBrukernavn() {

        return Brukernavn;
    }

    public void setBrukernavn(String brukernavn) {

        Brukernavn = brukernavn;
    }

    public String getFornavn() {

        return Fornavn;
    }

    public void setFornavn(String fornavn) {

        Fornavn = fornavn;
    }

    public String getEtternavn() {

        return Etternavn;
    }

    public void setEtternavn(String etternavn) {

        Etternavn = etternavn;
    }

    public Date getAnsettelsesDato() {

        return AnsettelsesDato;
    }

    public void setAnsettelsesDato(Date ansettelsesDato) {

        AnsettelsesDato = ansettelsesDato;
    }

    public String getStilling() {

        return Stilling;
    }

    public void setStilling(String stilling) {

        Stilling = stilling;
    }

    public double getManedslonn() {

        return Manedslonn;
    }

    public void setManedslonn(double manedslonn) {

        Manedslonn = manedslonn;
    }

    public String getAvdeling() {

        return Avdeling;
    }

    public void setAvdeling(String avdeling) {

        Avdeling = avdeling;
    }

    public boolean isErSjef() {

        return erSjef;
    }

    public void setErSjef(boolean erSjef) {

        this.erSjef = erSjef;
    }

    public List<Prosjektdeltagelse> getDeltagelser() {

        return deltagelser;
    }

    public void setDeltagelser(List<Prosjektdeltagelse> deltagelser) {

        this.deltagelser = deltagelser;
    }
}