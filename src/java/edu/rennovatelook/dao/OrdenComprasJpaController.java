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
import edu.rennovatelook.entity.OrdenCompras;
import edu.rennovatelook.entity.Productos;

/**
 *
 * @author usuario
 */
public class OrdenComprasJpaController implements Serializable {

    public OrdenComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompras ordenCompras) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos idFkProducto = ordenCompras.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto = em.getReference(idFkProducto.getClass(), idFkProducto.getIdProducto());
                ordenCompras.setIdFkProducto(idFkProducto);
            }
            em.persist(ordenCompras);
            if (idFkProducto != null) {
                idFkProducto.getOrdenComprasCollection().add(ordenCompras);
                idFkProducto = em.merge(idFkProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenCompras ordenCompras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompras persistentOrdenCompras = em.find(OrdenCompras.class, ordenCompras.getIdOrdenCompra());
            Productos idFkProductoOld = persistentOrdenCompras.getIdFkProducto();
            Productos idFkProductoNew = ordenCompras.getIdFkProducto();
            if (idFkProductoNew != null) {
                idFkProductoNew = em.getReference(idFkProductoNew.getClass(), idFkProductoNew.getIdProducto());
                ordenCompras.setIdFkProducto(idFkProductoNew);
            }
            ordenCompras = em.merge(ordenCompras);
            if (idFkProductoOld != null && !idFkProductoOld.equals(idFkProductoNew)) {
                idFkProductoOld.getOrdenComprasCollection().remove(ordenCompras);
                idFkProductoOld = em.merge(idFkProductoOld);
            }
            if (idFkProductoNew != null && !idFkProductoNew.equals(idFkProductoOld)) {
                idFkProductoNew.getOrdenComprasCollection().add(ordenCompras);
                idFkProductoNew = em.merge(idFkProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenCompras.getIdOrdenCompra();
                if (findOrdenCompras(id) == null) {
                    throw new NonexistentEntityException("The ordenCompras with id " + id + " no longer exists.");
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
            OrdenCompras ordenCompras;
            try {
                ordenCompras = em.getReference(OrdenCompras.class, id);
                ordenCompras.getIdOrdenCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenCompras with id " + id + " no longer exists.", enfe);
            }
            Productos idFkProducto = ordenCompras.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto.getOrdenComprasCollection().remove(ordenCompras);
                idFkProducto = em.merge(idFkProducto);
            }
            em.remove(ordenCompras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenCompras> findOrdenComprasEntities() {
        return findOrdenComprasEntities(true, -1, -1);
    }

    public List<OrdenCompras> findOrdenComprasEntities(int maxResults, int firstResult) {
        return findOrdenComprasEntities(false, maxResults, firstResult);
    }

    private List<OrdenCompras> findOrdenComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenCompras.class));
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

    public OrdenCompras findOrdenCompras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenCompras> rt = cq.from(OrdenCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
