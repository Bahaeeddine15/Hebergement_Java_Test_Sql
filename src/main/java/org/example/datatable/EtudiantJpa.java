package org.example.datatable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class EtudiantJpa {

    private EntityManagerFactory emf;

    public EtudiantJpa() {
        emf = Persistence.createEntityManagerFactory("jpa-unit");
    }

    public List<Etudiant> getAllEtudiants() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Etudiant e", Etudiant.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void ajouter(Etudiant etudiant) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(etudiant);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void modifier(Etudiant etudiant) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(etudiant);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();Etudiant etudiant = em.find(Etudiant.class, id);
            if (etudiant != null) {
                em.remove(etudiant);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
