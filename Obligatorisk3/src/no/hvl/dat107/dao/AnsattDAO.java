package no.hvl.dat107.dao;

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

    public Ansatt finnAnsattMedBrukernavn(String brukernavn) {

        EntityManager em = emf.createEntityManager();

        Ansatt ansatt = null;
        try {
            String queryString = "SELECT a FROM Ansatt a WHERE a.Brukernavn = :brukernavn";
            TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
            query.setParameter("brukernavn", brukernavn);
            ansatt = query.getSingleResult();
        } catch (NoResultException e) {
            // Return null if no ansatt found with given brukernavn
        } finally {
            em.close();
        }
        return ansatt;
    }



}