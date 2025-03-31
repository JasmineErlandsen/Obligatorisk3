package no.hvl.dat107.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class Prosjekt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProsjektID;
    private String ProsjektNavn;
    private String ProsjektBeskrivelse;

    @OneToMany(mappedBy="prosjekt")
    private List<Prosjektdeltagelse> deltagelser;

    public void skrivUt(String innrykk) {
        System.out.printf("%sProsjekt nr %d: %s", innrykk, ProsjektID, ProsjektNavn);
    }

    public void skrivUtMedAnsatte() {
        System.out.println();
        skrivUt("");
        deltagelser.forEach(a -> a.skrivUt("\n   "));
    }

    public void leggTilProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.add(prosjektdeltagelse);
    }

    public void fjernProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
        deltagelser.remove(prosjektdeltagelse);
    }

    public int getProsjektID() {
        return ProsjektID;
    }

    public void setProsjektID(int prosjektID) {
        ProsjektID = prosjektID;
    }

    public String getProsjektNavn() {
        return ProsjektNavn;
    }

    public void setProsjektNavn(String prosjektNavn) {
        ProsjektNavn = prosjektNavn;
    }

    public String getProsjektBeskrivelse() {
        return ProsjektBeskrivelse;
    }

    public void setProsjektBeskrivelse(String prosjektBeskrivelse) {
        ProsjektBeskrivelse = prosjektBeskrivelse;
    }

    public void setDeltagelser(List<Prosjektdeltagelse> deltagelser) {
        this.deltagelser = deltagelser;
    }

    public List<Prosjektdeltagelse> getDeltagelser() {
        return deltagelser;
    }


}
