package no.hvl.dat107.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat107.entity.Avdeling;

import java.util.Map;

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
}
