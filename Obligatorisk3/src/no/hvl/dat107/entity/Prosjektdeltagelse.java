package no.hvl.dat107.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "Oblig3")
public class Prosjektdeltagelse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deltagelseid;

    private int timer;
    private String rolle;

    @ManyToOne
    @JoinColumn(name="ansattid")
    private Ansatt ansatt;

    @ManyToOne
    @JoinColumn(name="prosjektid")
    private Prosjekt prosjekt;

    public Prosjektdeltagelse() {}

    public Prosjektdeltagelse(Ansatt ansatt, Prosjekt prosjekt, int timer, String rolle) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.timer = timer;
        this.rolle = rolle;
        //Hvis vi gjør dette her slipper vi å gjøre det i DAO
        ansatt.leggTilProsjektdeltagelse(this);
        prosjekt.leggTilProsjektdeltagelse(this);
    }

    public void skrivUt(String innrykk) {
        System.out.printf("%sDeltagelse: %s %s, %s, %d timer", innrykk,
                ansatt.getFornavn(), ansatt.getEtternavn(), prosjekt.getProsjektNavn(), timer, rolle);
    }

}
