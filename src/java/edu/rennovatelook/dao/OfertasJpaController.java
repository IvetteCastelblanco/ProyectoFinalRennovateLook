/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.dao;

import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Ofertas;

/**
 *
 * @author usuario
 */
public class OfertasJpaController implements Serializable {

    public OfertasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ofertas ofertas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idFkCliente = ofertas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente = em.getReference(idFkCliente.getClass(), idFkCliente.getIdCliente());
                ofertas.setIdFkCliente(idFkCliente);
            }
            em.persist(ofertas);
            if (idFkCliente != null) {
                idFkCliente.getOfertasCollection().add(ofertas);
                idFkCliente = em.merge(idFkCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ofertas ofertas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ofertas persistentOfertas = em.find(Ofertas.class, ofertas.getIdOferta());
            Clientes idFkClienteOld = persistentOfertas.getIdFkCliente();
            Clientes idFkClienteNew = ofertas.getIdFkCliente();
            if (idFkClienteNew != null) {
                idFkClienteNew = em.getReference(idFkClienteNew.getClass(), idFkClienteNew.getIdCliente());
                ofertas.setIdFkCliente(idFkClienteNew);
            }
            ofertas = em.merge(ofertas);
            if (idFkClienteOld != null && !idFkClienteOld.equals(idFkClienteNew)) {
                idFkClienteOld.getOfertasCollection().remove(ofertas);
                idFkClienteOld = em.merge(idFkClienteOld);
            }
            if (idFkClienteNew != null && !idFkClienteNew.equals(idFkClienteOld)) {
                idFkClienteNew.getOfertasCollection().add(ofertas);
                idFkClienteNew = em.merge(idFkClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ofertas.getIdOferta();
                if (findOfertas(id) == null) {
                    throw new NonexistentEntityException("The ofertas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ofertas ofertas;
            try {
                ofertas = em.getReference(Ofertas.class, id);
                ofertas.getIdOferta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ofertas with id " + id + " no longer exists.", enfe);
            }
            Clientes idFkCliente = ofertas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente.getOfertasCollection().remove(ofertas);
                idFkCliente = em.merge(idFkCliente);
            }
            em.remove(ofertas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ofertas> findOfertasEntities() {
        return findOfertasEntities(true, -1, -1);
    }

    public List<Ofertas> findOfertasEntities(int maxResults, int firstResult) {
        return findOfertasEntities(false, maxResults, firstResult);
    }

    private List<Ofertas> findOfertasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ofertas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ofertas findOfertas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ofertas.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfertasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ofertas> rt = cq.from(Ofertas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
