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
import edu.rennovatelook.entity.Citas;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Estilistas;
import edu.rennovatelook.entity.Productos;
import edu.rennovatelook.entity.Empleados;

/**
 *
 * @author usuario
 */
public class CitasJpaController implements Serializable {

    public CitasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citas citas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idFkCliente = citas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente = em.getReference(idFkCliente.getClass(), idFkCliente.getIdCliente());
                citas.setIdFkCliente(idFkCliente);
            }
            Estilistas idFkEstilista = citas.getIdFkEstilista();
            if (idFkEstilista != null) {
                idFkEstilista = em.getReference(idFkEstilista.getClass(), idFkEstilista.getIdEstilista());
                citas.setIdFkEstilista(idFkEstilista);
            }
            Productos idFkProducto = citas.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto = em.getReference(idFkProducto.getClass(), idFkProducto.getIdProducto());
                citas.setIdFkProducto(idFkProducto);
            }
            Empleados idFkEmpleado = citas.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado = em.getReference(idFkEmpleado.getClass(), idFkEmpleado.getIdEmpleado());
                citas.setIdFkEmpleado(idFkEmpleado);
            }
            em.persist(citas);
            if (idFkCliente != null) {
                idFkCliente.getCitasCollection().add(citas);
                idFkCliente = em.merge(idFkCliente);
            }
            if (idFkEstilista != null) {
                idFkEstilista.getCitasCollection().add(citas);
                idFkEstilista = em.merge(idFkEstilista);
            }
            if (idFkProducto != null) {
                idFkProducto.getCitasCollection().add(citas);
                idFkProducto = em.merge(idFkProducto);
            }
            if (idFkEmpleado != null) {
                idFkEmpleado.getCitasCollection().add(citas);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citas citas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas persistentCitas = em.find(Citas.class, citas.getIdCita());
            Clientes idFkClienteOld = persistentCitas.getIdFkCliente();
            Clientes idFkClienteNew = citas.getIdFkCliente();
            Estilistas idFkEstilistaOld = persistentCitas.getIdFkEstilista();
            Estilistas idFkEstilistaNew = citas.getIdFkEstilista();
            Productos idFkProductoOld = persistentCitas.getIdFkProducto();
            Productos idFkProductoNew = citas.getIdFkProducto();
            Empleados idFkEmpleadoOld = persistentCitas.getIdFkEmpleado();
            Empleados idFkEmpleadoNew = citas.getIdFkEmpleado();
            if (idFkClienteNew != null) {
                idFkClienteNew = em.getReference(idFkClienteNew.getClass(), idFkClienteNew.getIdCliente());
                citas.setIdFkCliente(idFkClienteNew);
            }
            if (idFkEstilistaNew != null) {
                idFkEstilistaNew = em.getReference(idFkEstilistaNew.getClass(), idFkEstilistaNew.getIdEstilista());
                citas.setIdFkEstilista(idFkEstilistaNew);
            }
            if (idFkProductoNew != null) {
                idFkProductoNew = em.getReference(idFkProductoNew.getClass(), idFkProductoNew.getIdProducto());
                citas.setIdFkProducto(idFkProductoNew);
            }
            if (idFkEmpleadoNew != null) {
                idFkEmpleadoNew = em.getReference(idFkEmpleadoNew.getClass(), idFkEmpleadoNew.getIdEmpleado());
                citas.setIdFkEmpleado(idFkEmpleadoNew);
            }
            citas = em.merge(citas);
            if (idFkClienteOld != null && !idFkClienteOld.equals(idFkClienteNew)) {
                idFkClienteOld.getCitasCollection().remove(citas);
                idFkClienteOld = em.merge(idFkClienteOld);
            }
            if (idFkClienteNew != null && !idFkClienteNew.equals(idFkClienteOld)) {
                idFkClienteNew.getCitasCollection().add(citas);
                idFkClienteNew = em.merge(idFkClienteNew);
            }
            if (idFkEstilistaOld != null && !idFkEstilistaOld.equals(idFkEstilistaNew)) {
                idFkEstilistaOld.getCitasCollection().remove(citas);
                idFkEstilistaOld = em.merge(idFkEstilistaOld);
            }
            if (idFkEstilistaNew != null && !idFkEstilistaNew.equals(idFkEstilistaOld)) {
                idFkEstilistaNew.getCitasCollection().add(citas);
                idFkEstilistaNew = em.merge(idFkEstilistaNew);
            }
            if (idFkProductoOld != null && !idFkProductoOld.equals(idFkProductoNew)) {
                idFkProductoOld.getCitasCollection().remove(citas);
                idFkProductoOld = em.merge(idFkProductoOld);
            }
            if (idFkProductoNew != null && !idFkProductoNew.equals(idFkProductoOld)) {
                idFkProductoNew.getCitasCollection().add(citas);
                idFkProductoNew = em.merge(idFkProductoNew);
            }
            if (idFkEmpleadoOld != null && !idFkEmpleadoOld.equals(idFkEmpleadoNew)) {
                idFkEmpleadoOld.getCitasCollection().remove(citas);
                idFkEmpleadoOld = em.merge(idFkEmpleadoOld);
            }
            if (idFkEmpleadoNew != null && !idFkEmpleadoNew.equals(idFkEmpleadoOld)) {
                idFkEmpleadoNew.getCitasCollection().add(citas);
                idFkEmpleadoNew = em.merge(idFkEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = citas.getIdCita();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
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
            Citas citas;
            try {
                citas = em.getReference(Citas.class, id);
                citas.getIdCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citas with id " + id + " no longer exists.", enfe);
            }
            Clientes idFkCliente = citas.getIdFkCliente();
            if (idFkCliente != null) {
                idFkCliente.getCitasCollection().remove(citas);
                idFkCliente = em.merge(idFkCliente);
            }
            Estilistas idFkEstilista = citas.getIdFkEstilista();
            if (idFkEstilista != null) {
                idFkEstilista.getCitasCollection().remove(citas);
                idFkEstilista = em.merge(idFkEstilista);
            }
            Productos idFkProducto = citas.getIdFkProducto();
            if (idFkProducto != null) {
                idFkProducto.getCitasCollection().remove(citas);
                idFkProducto = em.merge(idFkProducto);
            }
            Empleados idFkEmpleado = citas.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado.getCitasCollection().remove(citas);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.remove(citas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Citas> findCitasEntities() {
        return findCitasEntities(true, -1, -1);
    }

    public List<Citas> findCitasEntities(int maxResults, int firstResult) {
        return findCitasEntities(false, maxResults, firstResult);
    }

    private List<Citas> findCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citas.class));
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

    public Citas findCitas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citas> rt = cq.from(Citas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
