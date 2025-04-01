package no.hvl.dat107.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "oblig3")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int avdelingID;

    private String navn;

    @OneToOne
    @JoinColumn(name = "sjef_id")
    private Ansatt sjef;

    @OneToMany(mappedBy = "avdeling")
    private List<Ansatt> ansatte;

    public int getAvdelingID() {

        return avdelingID;
    }

    public void setAvdelingID(int avdelingID) {

        this.avdelingID = avdelingID;
    }

    public String getNavn() {

        return navn;
    }

    public void setNavn(String navn) {

        this.navn = navn;
    }

    public Ansatt getSjef() {

        return sjef;
    }

    public void setSjef(Ansatt sjef) {
        this.sjef = sjef;
    }

    public List<Ansatt> getAnsatte() {

        return ansatte;
    }

    public void setAnsatte(List<Ansatt> ansatte) {
        this.ansatte = ansatte;
    }

    @Override
    public String toString() {
        return "Avdeling{" +
                "avdelingID=" + avdelingID +
                ", navn='" + navn + '\'' +
                ", sjef=" + (sjef != null ? sjef.getFornavn() + " " + sjef.getEtternavn() : "Ingen") +
                '}';
    }
}
