package no.hvl.dat107;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;

public class Main {

    public static void main(String[] args) {

        AnsattDAO ansattDAO = new AnsattDAO();
        ProsjektDAO prosjektDAO = new ProsjektDAO();

        Ansatt a2 = ansattDAO.finnAnsattMedId(2);

        // Test enkel ansatt-informasjon
        a2.skrivUt("");
        System.out.println("\n"); // Legger til mellomrom for bedre lesbarhet

        // Test get-metoder og skriv ut detaljert informasjon
        System.out.println("--- Detaljert Ansattinformasjon ---");
        System.out.println("Ansatt ID: " + a2.getAnsattID());
        System.out.println("Brukernavn: " + a2.getBrukernavn());
        System.out.println("Fornavn: " + a2.getFornavn());
        System.out.println("Etternavn: " + a2.getEtternavn());
        System.out.println("Stilling: " + a2.getStilling());
        System.out.println("Avdeling: " + a2.getAvdeling());
        System.out.println("Månedslønn: " + a2.getManedslonn());
        System.out.println("Ansettelsesdato: " + a2.getAnsettelsesDato());
        System.out.println("Er sjef: " + (a2.isErSjef() ? "Ja" : "Nei"));

        System.out.print("Tester");
//        Prosjekt p2 = prosjektDAO.finnProsjektMedId(2);
//        p2.skrivUtMedAnsatte();
//
//        Prosjekt p3 = prosjektDAO.finnProsjektMedId(3);
//        p3.skrivUtMedAnsatte();
//
//        ansattDAO.registrerProsjektdeltagelse(a2.getAnsattID(), p3.getId());
//        a2 = ansattDAO.finnAnsattMedId(2);
//        p3 = prosjektDAO.finnProsjektMedId(3);
//        a2.skrivUtMedProsjekter();
//        p3.skrivUtMedAnsatte();
//
//        ansattDAO.slettProsjektdeltagelse(a2.getAnsattID(), p3.getId());
//        a2 = ansattDAO.finnAnsattMedId(2);
//        p3 = prosjektDAO.finnProsjektMedId(3);
//        a2.skrivUtMedProsjekter();
//        p3.skrivUtMedAnsatte();
      }

}