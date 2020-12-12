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
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Empleados;
import edu.rennovatelook.entity.Productos;
import edu.rennovatelook.entity.Ventas;

/**
 *
 * @author usuario
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idFkCliente = ventas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente = em.getReference(idFkCliente.getClass(), idFkCliente.getIdCliente());
                ventas.setIdFkCliente(idFkCliente);
            }
            Empleados idFkEmpleado = ventas.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado = em.getReference(idFkEmpleado.getClass(), idFkEmpleado.getIdEmpleado());
                ventas.setIdFkEmpleado(idFkEmpleado);
            }
            Productos idFkProducto = ventas.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto = em.getReference(idFkProducto.getClass(), idFkProducto.getIdProducto());
                ventas.setIdFkProducto(idFkProducto);
            }
            em.persist(ventas);
            if (idFkCliente != null) {
                idFkCliente.getVentasCollection().add(ventas);
                idFkCliente = em.merge(idFkCliente);
            }
            if (idFkEmpleado != null) {
                idFkEmpleado.getVentasCollection().add(ventas);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            if (idFkProducto != null) {
                idFkProducto.getVentasCollection().add(ventas);
                idFkProducto = em.merge(idFkProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdVenta());
            Clientes idFkClienteOld = persistentVentas.getIdFkCliente();
            Clientes idFkClienteNew = ventas.getIdFkCliente();
            Empleados idFkEmpleadoOld = persistentVentas.getIdFkEmpleado();
            Empleados idFkEmpleadoNew = ventas.getIdFkEmpleado();
            Productos idFkProductoOld = persistentVentas.getIdFkProducto();
            Productos idFkProductoNew = ventas.getIdFkProducto();
            if (idFkClienteNew != null) {
                idFkClienteNew = em.getReference(idFkClienteNew.getClass(), idFkClienteNew.getIdCliente());
                ventas.setIdFkCliente(idFkClienteNew);
            }
            if (idFkEmpleadoNew != null) {
                idFkEmpleadoNew = em.getReference(idFkEmpleadoNew.getClass(), idFkEmpleadoNew.getIdEmpleado());
                ventas.setIdFkEmpleado(idFkEmpleadoNew);
            }
            if (idFkProductoNew != null) {
                idFkProductoNew = em.getReference(idFkProductoNew.getClass(), idFkProductoNew.getIdProducto());
                ventas.setIdFkProducto(idFkProductoNew);
            }
            ventas = em.merge(ventas);
            if (idFkClienteOld != null && !idFkClienteOld.equals(idFkClienteNew)) {
                idFkClienteOld.getVentasCollection().remove(ventas);
                idFkClienteOld = em.merge(idFkClienteOld);
            }
            if (idFkClienteNew != null && !idFkClienteNew.equals(idFkClienteOld)) {
                idFkClienteNew.getVentasCollection().add(ventas);
                idFkClienteNew = em.merge(idFkClienteNew);
            }
            if (idFkEmpleadoOld != null && !idFkEmpleadoOld.equals(idFkEmpleadoNew)) {
                idFkEmpleadoOld.getVentasCollection().remove(ventas);
                idFkEmpleadoOld = em.merge(idFkEmpleadoOld);
            }
            if (idFkEmpleadoNew != null && !idFkEmpleadoNew.equals(idFkEmpleadoOld)) {
                idFkEmpleadoNew.getVentasCollection().add(ventas);
                idFkEmpleadoNew = em.merge(idFkEmpleadoNew);
            }
            if (idFkProductoOld != null && !idFkProductoOld.equals(idFkProductoNew)) {
                idFkProductoOld.getVentasCollection().remove(ventas);
                idFkProductoOld = em.merge(idFkProductoOld);
            }
            if (idFkProductoNew != null && !idFkProductoNew.equals(idFkProductoOld)) {
                idFkProductoNew.getVentasCollection().add(ventas);
                idFkProductoNew = em.merge(idFkProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getIdVenta();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            Clientes idFkCliente = ventas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente.getVentasCollection().remove(ventas);
                idFkCliente = em.merge(idFkCliente);
            }
            Empleados idFkEmpleado = ventas.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado.getVentasCollection().remove(ventas);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            Productos idFkProducto = ventas.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto.getVentasCollection().remove(ventas);
                idFkProducto = em.merge(idFkProducto);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
