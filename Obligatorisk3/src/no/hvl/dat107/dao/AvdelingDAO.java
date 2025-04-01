package no.hvl.dat107.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AvdelingDAO {

    private EntityManagerFactory emf;

    public AvdelingDAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU",
                Map.of("jakarta.persistence.jdbc.password", "Kikerter3rdigg"));
    }

    public Avdeling finnAvdelingMedID(int id){
        EntityManager em = emf.createEntityManager();
        Avdeling avdeling = null;
        try{
            avdeling = em.find(Avdeling.class, id);
        }finally{
            em.close();
        }
        return avdeling;
    }

    public List<Ansatt> alleAnsatteIAvdeling(Avdeling avdeling) {
        EntityManager em = emf.createEntityManager();
        List<Ansatt> ansatte = new ArrayList<>();

        try{
            String queryString = "SELECT ans FROM Ansatt ans WHERE ans.avdeling = :avdeling";
            TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
            query.setParameter("avdeling", avdeling); //Setter parameteren
            ansatte = query.getResultList();
        }finally{
            em.close();
        }
        return ansatte;
    }

    public void oppdaterSjef(Avdeling avdeling, Ansatt nySjef) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Ansatt gammelSjef = em.createQuery( "SELECT a FROM Ansatt a WHERE a.avdeling = :avdeling AND a.erSjef = true", Ansatt.class)
                    .setParameter("avdeling", avdeling)
                    .getSingleResult();

            if (gammelSjef != null) {
                gammelSjef.setErSjef(false);
                em.merge(gammelSjef);
            }

            nySjef.setErSjef(true);
            em.merge(nySjef);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void opprettAvdeling(String navn, Ansatt nySjef) {
        EntityManager em = emf.createEntityManager();
        AnsattDAO ansattDAO = new AnsattDAO();
        Scanner s = new Scanner(System.in);

        try{
            em.getTransaction().begin(); //Starter transaksjon
            Avdeling nyAvdeling = new Avdeling();//Oppretter ny avdeling og setter sjef
            nyAvdeling.setAvdelingsnavn(navn);
            em.persist(nyAvdeling); //Lagrer i databasen

            oppdaterSjef(nyAvdeling, nySjef);//Oppdaterer sjefens avdeling
            nySjef.setAvdeling(nyAvdeling);

            em.merge(nySjef);

            em.getTransaction().commit(); //Fullfører transaksjonen
        }catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();; //Ruller tilbake hvis noe går galt
            }
            e.printStackTrace();
        }finally {
            em.close(); //Lukker EntityManager
        }
    }

    public List<Avdeling> alleAvdelinger(){
        EntityManager em = emf.createEntityManager();
        List<Avdeling> avdelinger = new ArrayList<>();
        try{
            String queryString = "SELECT av FROM Avdeling av";
            TypedQuery<Avdeling> query = em.createQuery(queryString, Avdeling.class);
            avdelinger = query.getResultList();
        }finally{
            em.close();
        }
        return avdelinger;
    }
}
