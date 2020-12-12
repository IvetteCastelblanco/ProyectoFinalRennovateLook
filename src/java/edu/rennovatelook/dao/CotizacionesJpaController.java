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
import edu.rennovatelook.entity.Cotizaciones;
import edu.rennovatelook.entity.Empleados;

/**
 *
 * @author usuario
 */
public class CotizacionesJpaController implements Serializable {

    public CotizacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cotizaciones cotizaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idFkCliente = cotizaciones.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente = em.getReference(idFkCliente.getClass(), idFkCliente.getIdCliente());
                cotizaciones.setIdFkCliente(idFkCliente);
            }
            Empleados idFkEmpleado = cotizaciones.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado = em.getReference(idFkEmpleado.getClass(), idFkEmpleado.getIdEmpleado());
                cotizaciones.setIdFkEmpleado(idFkEmpleado);
            }
            em.persist(cotizaciones);
            if (idFkCliente != null) {
                idFkCliente.getCotizacionesCollection().add(cotizaciones);
                idFkCliente = em.merge(idFkCliente);
            }
            if (idFkEmpleado != null) {
                idFkEmpleado.getCotizacionesCollection().add(cotizaciones);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cotizaciones cotizaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cotizaciones persistentCotizaciones = em.find(Cotizaciones.class, cotizaciones.getIdCotizacion());
            Clientes idFkClienteOld = persistentCotizaciones.getIdFkCliente();
            Clientes idFkClienteNew = cotizaciones.getIdFkCliente();
            Empleados idFkEmpleadoOld = persistentCotizaciones.getIdFkEmpleado();
            Empleados idFkEmpleadoNew = cotizaciones.getIdFkEmpleado();
            if (idFkClienteNew != null) {
                idFkClienteNew = em.getReference(idFkClienteNew.getClass(), idFkClienteNew.getIdCliente());
                cotizaciones.setIdFkCliente(idFkClienteNew);
            }
            if (idFkEmpleadoNew != null) {
                idFkEmpleadoNew = em.getReference(idFkEmpleadoNew.getClass(), idFkEmpleadoNew.getIdEmpleado());
                cotizaciones.setIdFkEmpleado(idFkEmpleadoNew);
            }
            cotizaciones = em.merge(cotizaciones);
            if (idFkClienteOld != null && !idFkClienteOld.equals(idFkClienteNew)) {
                idFkClienteOld.getCotizacionesCollection().remove(cotizaciones);
                idFkClienteOld = em.merge(idFkClienteOld);
            }
            if (idFkClienteNew != null && !idFkClienteNew.equals(idFkClienteOld)) {
                idFkClienteNew.getCotizacionesCollection().add(cotizaciones);
                idFkClienteNew = em.merge(idFkClienteNew);
            }
            if (idFkEmpleadoOld != null && !idFkEmpleadoOld.equals(idFkEmpleadoNew)) {
                idFkEmpleadoOld.getCotizacionesCollection().remove(cotizaciones);
                idFkEmpleadoOld = em.merge(idFkEmpleadoOld);
            }
            if (idFkEmpleadoNew != null && !idFkEmpleadoNew.equals(idFkEmpleadoOld)) {
                idFkEmpleadoNew.getCotizacionesCollection().add(cotizaciones);
                idFkEmpleadoNew = em.merge(idFkEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cotizaciones.getIdCotizacion();
                if (findCotizaciones(id) == null) {
                    throw new NonexistentEntityException("The cotizaciones with id " + id + " no longer exists.");
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
            Cotizaciones cotizaciones;
            try {
                cotizaciones = em.getReference(Cotizaciones.class, id);
                cotizaciones.getIdCotizacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cotizaciones with id " + id + " no longer exists.", enfe);
            }
            Clientes idFkCliente = cotizaciones.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente.getCotizacionesCollection().remove(cotizaciones);
                idFkCliente = em.merge(idFkCliente);
            }
            Empleados idFkEmpleado = cotizaciones.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado.getCotizacionesCollection().remove(cotizaciones);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.remove(cotizaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cotizaciones> findCotizacionesEntities() {
        return findCotizacionesEntities(true, -1, -1);
    }

    public List<Cotizaciones> findCotizacionesEntities(int maxResults, int firstResult) {
        return findCotizacionesEntities(false, maxResults, firstResult);
    }

    private List<Cotizaciones> findCotizacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cotizaciones.class));
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

    public Cotizaciones findCotizaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cotizaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getCotizacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cotizaciones> rt = cq.from(Cotizaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
