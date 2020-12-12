/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.dao;

import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import edu.rennovatelook.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.rennovatelook.entity.Estilistas;
import edu.rennovatelook.entity.Empleados;
import edu.rennovatelook.entity.Horarios;

/**
 *
 * @author usuario
 */
public class HorariosJpaController implements Serializable {

    public HorariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horarios horarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estilistas idFkEstilista = horarios.getIdFkEstilista();
            if (idFkEstilista != null) {
                idFkEstilista = em.getReference(idFkEstilista.getClass(), idFkEstilista.getIdEstilista());
                horarios.setIdFkEstilista(idFkEstilista);
            }
            Empleados idFkEmpleado = horarios.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado = em.getReference(idFkEmpleado.getClass(), idFkEmpleado.getIdEmpleado());
                horarios.setIdFkEmpleado(idFkEmpleado);
            }
            em.persist(horarios);
            if (idFkEstilista != null) {
                idFkEstilista.getHorariosCollection().add(horarios);
                idFkEstilista = em.merge(idFkEstilista);
            }
            if (idFkEmpleado != null) {
                idFkEmpleado.getHorariosCollection().add(horarios);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHorarios(horarios.getIdHorario()) != null) {
                throw new PreexistingEntityException("Horarios " + horarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horarios horarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios persistentHorarios = em.find(Horarios.class, horarios.getIdHorario());
            Estilistas idFkEstilistaOld = persistentHorarios.getIdFkEstilista();
            Estilistas idFkEstilistaNew = horarios.getIdFkEstilista();
            Empleados idFkEmpleadoOld = persistentHorarios.getIdFkEmpleado();
            Empleados idFkEmpleadoNew = horarios.getIdFkEmpleado();
            if (idFkEstilistaNew != null) {
                idFkEstilistaNew = em.getReference(idFkEstilistaNew.getClass(), idFkEstilistaNew.getIdEstilista());
                horarios.setIdFkEstilista(idFkEstilistaNew);
            }
            if (idFkEmpleadoNew != null) {
                idFkEmpleadoNew = em.getReference(idFkEmpleadoNew.getClass(), idFkEmpleadoNew.getIdEmpleado());
                horarios.setIdFkEmpleado(idFkEmpleadoNew);
            }
            horarios = em.merge(horarios);
            if (idFkEstilistaOld != null && !idFkEstilistaOld.equals(idFkEstilistaNew)) {
                idFkEstilistaOld.getHorariosCollection().remove(horarios);
                idFkEstilistaOld = em.merge(idFkEstilistaOld);
            }
            if (idFkEstilistaNew != null && !idFkEstilistaNew.equals(idFkEstilistaOld)) {
                idFkEstilistaNew.getHorariosCollection().add(horarios);
                idFkEstilistaNew = em.merge(idFkEstilistaNew);
            }
            if (idFkEmpleadoOld != null && !idFkEmpleadoOld.equals(idFkEmpleadoNew)) {
                idFkEmpleadoOld.getHorariosCollection().remove(horarios);
                idFkEmpleadoOld = em.merge(idFkEmpleadoOld);
            }
            if (idFkEmpleadoNew != null && !idFkEmpleadoNew.equals(idFkEmpleadoOld)) {
                idFkEmpleadoNew.getHorariosCollection().add(horarios);
                idFkEmpleadoNew = em.merge(idFkEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horarios.getIdHorario();
                if (findHorarios(id) == null) {
                    throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.");
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
            Horarios horarios;
            try {
                horarios = em.getReference(Horarios.class, id);
                horarios.getIdHorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.", enfe);
            }
            Estilistas idFkEstilista = horarios.getIdFkEstilista();
            if (idFkEstilista != null) {
                idFkEstilista.getHorariosCollection().remove(horarios);
                idFkEstilista = em.merge(idFkEstilista);
            }
            Empleados idFkEmpleado = horarios.getIdFkEmpleado();
            if (idFkEmpleado != null) {
                idFkEmpleado.getHorariosCollection().remove(horarios);
                idFkEmpleado = em.merge(idFkEmpleado);
            }
            em.remove(horarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horarios> findHorariosEntities() {
        return findHorariosEntities(true, -1, -1);
    }

    public List<Horarios> findHorariosEntities(int maxResults, int firstResult) {
        return findHorariosEntities(false, maxResults, firstResult);
    }

    private List<Horarios> findHorariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horarios.class));
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

    public Horarios findHorarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horarios> rt = cq.from(Horarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
