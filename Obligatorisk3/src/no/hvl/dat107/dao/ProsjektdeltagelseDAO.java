package no.hvl.dat107.dao;

import jakarta.persistence.*;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltagelse;

import java.util.List;
import java.util.Map;

public class ProsjektdeltagelseDAO {

    private static EntityManagerFactory emf;

    public ProsjektdeltagelseDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU",
                Map.of("jakarta.persistence.jdbc.password", "Kikerter3rdigg"));
    }
    public static void registrerProsjektdeltagelse(int ansattId, int prosjektId) {

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

    public static void slettProsjektdeltagelse(int ansattId, int prosjektId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Prosjektdeltagelse pd = finnProsjektdeltagelse(ansattId, prosjektId);
            em.remove(pd);

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

    @SuppressWarnings("unused")
    private static Prosjektdeltagelse finnProsjektdeltagelse(int ansattId, int prosjektId) {

        String queryString = "SELECT pd FROM Prosjektdeltagelse pd "
                + "WHERE pd.ansatt.AnsattID = :ansattId AND pd.prosjekt.ProsjektID = :prosjektId";

        EntityManager em = emf.createEntityManager();

        Prosjektdeltagelse pd = null;
        try {
            TypedQuery<Prosjektdeltagelse> query
                    = em.createQuery(queryString, Prosjektdeltagelse.class);
            query.setParameter("ansattId", ansattId);
            query.setParameter("prosjektId", prosjektId);
            pd = query.getSingleResult();

        } catch (NoResultException e) {
            // e.printStackTrace();
        } finally {
            em.close();
        }
        return pd;
    }
}