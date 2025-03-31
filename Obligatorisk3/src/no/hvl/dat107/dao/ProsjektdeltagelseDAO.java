package no.hvl.dat107.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat107.entity.Prosjektdeltagelse;

import java.util.List;
import java.util.Map;

public class ProsjektdeltagelseDAO {

    private EntityManagerFactory emf;

    public ProsjektdeltagelseDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU",
                Map.of("jakarta.persistence.jdbc.password", "Kikerter3rdigg"));
    }
    public void lagreTimeregistrering(Prosjektdeltagelse timeregistrering) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(timeregistrering);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Prosjektdeltagelse finnTimeregistrering(int ansattId, int prosjektId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prosjektdeltagelse.class, new ProsjektdeltagelsePK(ansattId, prosjektId));
        } finally {
            em.close();
        }
    }

    public List<Prosjektdeltagelse> finnTimeregistreringerForAnsatt(int ansattId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM Prosjektdeltagelse t WHERE t.ansatt.AnsattID = :ansattID",
                Prosjektdeltagelse.class)
                .setParameter("ansattId", ansattId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prosjektdeltagelse> finnTimeregistreringerForProsjekt(int prosjektId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM Prosjektdeltagelse t WHERE t.prosjekt.ProsjektID = :prosjektId",
                Prosjektdeltagelse.class)
                .setParameter("prosjektId", prosjektId)
                .getResultList();
        } finally {
            em.close();
        }
    }
}