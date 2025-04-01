package no.hvl.dat107;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.entity.Avdeling;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.dao.ProsjektdeltagelseDAO;
import no.hvl.dat107.entity.Prosjektdeltagelse;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainAnsatt {

    private static AnsattDAO ansattDAO = new AnsattDAO();
    private static ProsjektDAO prosjektDAO = new ProsjektDAO();
    private static AvdelingDAO avdelingDAO = new AvdelingDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean fortsett = true;

        while (fortsett) {
            skrivHovedmenyAnsatt();
            int valg = scanner.nextInt();
            scanner.nextLine(); // Håndterer linebreak

            switch (valg) {
                case 1:
                    finnOgVisAnsatt();
                    break;
                case 2:
                    registrerProsjektdeltagelse();
                    break;
                case 3:
                    slettProsjektdeltagelse();
                    break;
                case 4:
                    visProsjektMedAnsatte();
                    break;
                case 5:
                    finnLonn();
                    break;
                case 6:
                    alleAnsatte();
                    break;
                case 7:
                    opprettNyAnsatt();
                    break;
                case 8:
                    opprettNyAvdeling();
                    break;
                case 0:
                    fortsett = false;
                    System.out.println("Programmet avsluttes.");
                    break;
                default:
                    System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
        scanner.close();
    }

    private static void skrivHovedmenyAnsatt() {
        System.out.println("\n--- Hovedmeny Ansatte ---");
        System.out.println("1. Finn og vis ansatt");
        System.out.println("2. Registrer prosjektdeltagelse");
        System.out.println("3. Slett prosjektdeltagelse");
        System.out.println("4. Vis prosjekt med ansatte");
        System.out.println("5. Månedslønn for ansatte");
        System.out.println("6. Oversikt over alle ansatte");
        System.out.println("7. Opprett ny ansatt");
        System.out.println("8. Lag ny avdeling med sjef");
        System.out.println("0. Avslutt");
        System.out.print("Valg: ");
    }

    private static void finnOgVisAnsatt() {
        System.out.print("Skriv inn ansatt-ID: ");
        int id = scanner.nextInt();
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
        
        if (ansatt != null) {
            System.out.println("\n--- Ansattinformasjon ---");
            ansatt.skrivUt("");
        } else {
            System.out.println("Fant ikke ansatt med ID " + id);
        }
    }

    private static void registrerProsjektdeltagelse() {
        System.out.print("Skriv inn ansatt-ID: ");
        int ansattId = scanner.nextInt();
        System.out.print("Skriv inn prosjekt-ID: ");
        int prosjektId = scanner.nextInt();

        ProsjektdeltagelseDAO.registrerProsjektdeltagelse(ansattId, prosjektId);
        System.out.println("Prosjektdeltagelse registrert.");
    }

    private static void slettProsjektdeltagelse() {
        System.out.print("Skriv inn ansatt-ID: ");
        int ansattId = scanner.nextInt();
        System.out.print("Skriv inn prosjekt-ID: ");
        int prosjektId = scanner.nextInt();

        ProsjektdeltagelseDAO.slettProsjektdeltagelse(ansattId, prosjektId);
        System.out.println("Prosjektdeltagelse slettet.");
    }

    private static void visProsjektMedAnsatte() {
        System.out.print("Skriv inn prosjekt-ID: ");
        int id = scanner.nextInt();
        Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(id);
        
        if (prosjekt != null) {
            prosjekt.skrivUtMedAnsatte();
        } else {
            System.out.println("Fant ikke prosjekt med ID " + id);
        }
    }

    private static void finnLonn() {
        System.out.print("Skriv inn Ansatt-ID: ");
        int ansattId = scanner.nextInt();
        Double ansattLonn = ansattDAO.finnLonnForAnsatt(ansattId);
        if (ansattLonn != null) {
            System.out.println("Skal lønnen oppdateres? 1 = ja, 0 = nei: ");
            int decision = scanner.nextInt();
            if(decision == 1) {
                ansattLonn = ansattDAO.oppdaterLonnForAnsatt(ansattId);
            }
            System.out.println("\n--- Månedslønn for ansatt ---");
            System.out.println(ansattLonn);
        }else {
            System.out.println("Fant ikke ansatt med ID " + ansattId);
        }
    }

    private static void opprettNyAnsatt() {
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.next();

        System.out.print("Fornavn: ");
        String fornavn = scanner.next();

        System.out.print("Etternavn: ");
        String etternavn = scanner.next();

        System.out.print("År for ansettelse (yyyy-mm-dd): ");
        String datoStr = scanner.next();
        Date dato = Date.valueOf(datoStr); // Konverter til SQL Date

        System.out.print("Stilling: ");
        scanner.nextLine(); // Clear newline
        String stilling = scanner.nextLine();

        System.out.print("Månedslønn: ");
        double lønn = scanner.nextDouble();

       /* System.out.print("Avdeling: ");
        System.out.print("Avdeling ID: ");
        int avdelingId = scanner.nextInt();
        Avdeling avdeling = new AvdelingDAO().finnAvdelingMedID(avdelingId);

        System.out.print("Er personen sjef? (true/false): ");
        boolean erSjef = scanner.nextBoolean();*/

        ansattDAO.opprettNyAnsatt(brukernavn, fornavn, etternavn, dato, stilling, lønn//, avdeling, erSjef
        );


        System.out.println("Ny ansatt registrert!");
    }


    private static void opprettNyAvdeling() {
        System.out.println("Skriv navn på ny avdeling: ");
        String navn = scanner.next();

        System.out.println("Skriv Ansatt-ID for ny sjef: ");
        Ansatt nysjef = ansattDAO.finnAnsattMedId(scanner.nextInt());

        avdelingDAO.opprettAvdeling(navn, nysjef);

        System.out.println("Ny avdeling opprettet!");
    }


    private static void alleAnsatte() {
         List<Ansatt> ansatte = ansattDAO.finnAlleAnsatte();
         for (int i = 0; i < ansatte.size(); i++) {
             System.out.println(ansatte.get(i));
         }
    }
}