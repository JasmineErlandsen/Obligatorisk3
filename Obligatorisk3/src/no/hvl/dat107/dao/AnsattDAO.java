package no.hvl.dat107.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltagelse;

//import static no.hvl.dat107.dao.ProsjektdeltagelseDAO.emf; //Hvorfor importer dere denne?

public class AnsattDAO {

    private EntityManagerFactory emf;

    public AnsattDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU",
                Map.of("jakarta.persistence.jdbc.password", "Kikerter3rdigg"));
    }

    public Ansatt finnAnsattMedId(int id) {

        EntityManager em = emf.createEntityManager();

        Ansatt ansatt = null;
        try {
            ansatt = em.find(Ansatt.class, id);
        } finally {
            em.close();
        }
        return ansatt;
    }

    public List<Ansatt> finnAlleAnsatte(){
        EntityManager em = emf.createEntityManager();
        List<Ansatt> ansatte = new ArrayList<>();

        try{
            String queryString = "SELECT ans FROM Ansatt ans";
            TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
            ansatte = query.getResultList();
        } finally {
            em.close();
        }
        return ansatte;
    }

    public Double finnLonnForAnsatt(int id) {
        EntityManager em = emf.createEntityManager();
        Double lønn = null;

        try {
            String queryString = "SELECT ans.Manedslonn FROM Ansatt ans WHERE ans.AnsattID = :AnsattID";
            TypedQuery<Double> query = em.createQuery(queryString, Double.class);
            query.setParameter("AnsattID", id);
            lønn = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Fant ikke ansatt for " + id);
        } catch (Exception e) {
            System.err.println("Error finding salary for employee " + id + ": " + e.getMessage());
        }
        finally {
            em.close();
        }

        return lønn;
    }

    public Double oppdaterLonnForAnsatt(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Double nyLonn = null;

        try {
            tx.begin();

            Ansatt ansatt = em.find(Ansatt.class, id);
            if (ansatt != null) {
                double nåværendeLønn = ansatt.getManedslonn();
                nyLonn = nåværendeLønn * 1.05;
                ansatt.setManedslonn(nyLonn);
                em.merge(ansatt);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Feil under oppdatering av lønn for ansatt " + id + ": " + e.getMessage());
        } finally {
            em.close();
        }

        return nyLonn;
    }

    public void opprettNyAnsatt(String brukernavn, String fornavn, String etternavn,
                                java.sql.Date ansettelsesdato, String stilling, double manedslonn
                                //, Avdeling avdeling, boolean erSjef
                                ) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Ansatt nyAnsatt = new Ansatt();
            nyAnsatt.setBrukernavn(brukernavn);
            nyAnsatt.setFornavn(fornavn);
            nyAnsatt.setEtternavn(etternavn);
            nyAnsatt.setAnsettelsesDato(ansettelsesdato);
            nyAnsatt.setStilling(stilling);
            nyAnsatt.setManedslonn(manedslonn);
          //  nyAnsatt.setAvdeling(avdeling);
          //  nyAnsatt.setErSjef(erSjef);

            em.persist(nyAnsatt); // Save to database
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Feil ved oppretting av ny ansatt: " + e.getMessage());
        } finally {
            em.close();
        }
    }




    public void registrerProsjektdeltagelse(int ansattId, int prosjektId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Ansatt a = em.find(Ansatt.class, ansattId);
            Prosjekt p = em.find(Prosjekt.class, prosjektId);

            Prosjektdeltagelse pd = new Prosjektdeltagelse(a, p, 0);

            em.persist(pd);

            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }

    }


}