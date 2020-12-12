/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.dao;

import edu.rennovatelook.dao.exceptions.IllegalOrphanException;
import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import edu.rennovatelook.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.rennovatelook.entity.Horarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import edu.rennovatelook.entity.Citas;
import edu.rennovatelook.entity.Cotizaciones;
import edu.rennovatelook.entity.Empleados;
import edu.rennovatelook.entity.Ventas;
import javax.persistence.PersistenceContext;

/**
 *
 * @author usuario
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) throws PreexistingEntityException, Exception {
        if (empleados.getHorariosCollection() == null) {
            empleados.setHorariosCollection(new ArrayList<Horarios>());
        }
        if (empleados.getCitasCollection() == null) {
            empleados.setCitasCollection(new ArrayList<Citas>());
        }
        if (empleados.getCotizacionesCollection() == null) {
            empleados.setCotizacionesCollection(new ArrayList<Cotizaciones>());
        }
        if (empleados.getVentasCollection() == null) {
            empleados.setVentasCollection(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Horarios> attachedHorariosCollection = new ArrayList<Horarios>();
            for (Horarios horariosCollectionHorariosToAttach : empleados.getHorariosCollection()) {
                horariosCollectionHorariosToAttach = em.getReference(horariosCollectionHorariosToAttach.getClass(), horariosCollectionHorariosToAttach.getIdHorario());
                attachedHorariosCollection.add(horariosCollectionHorariosToAttach);
            }
            empleados.setHorariosCollection(attachedHorariosCollection);
            Collection<Citas> attachedCitasCollection = new ArrayList<Citas>();
            for (Citas citasCollectionCitasToAttach : empleados.getCitasCollection()) {
                citasCollectionCitasToAttach = em.getReference(citasCollectionCitasToAttach.getClass(), citasCollectionCitasToAttach.getIdCita());
                attachedCitasCollection.add(citasCollectionCitasToAttach);
            }
            empleados.setCitasCollection(attachedCitasCollection);
            Collection<Cotizaciones> attachedCotizacionesCollection = new ArrayList<Cotizaciones>();
            for (Cotizaciones cotizacionesCollectionCotizacionesToAttach : empleados.getCotizacionesCollection()) {
                cotizacionesCollectionCotizacionesToAttach = em.getReference(cotizacionesCollectionCotizacionesToAttach.getClass(), cotizacionesCollectionCotizacionesToAttach.getIdCotizacion());
                attachedCotizacionesCollection.add(cotizacionesCollectionCotizacionesToAttach);
            }
            empleados.setCotizacionesCollection(attachedCotizacionesCollection);
            Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
            for (Ventas ventasCollectionVentasToAttach : empleados.getVentasCollection()) {
                ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdVenta());
                attachedVentasCollection.add(ventasCollectionVentasToAttach);
            }
            empleados.setVentasCollection(attachedVentasCollection);
            em.persist(empleados);
            for (Horarios horariosCollectionHorarios : empleados.getHorariosCollection()) {
                Empleados oldIdFkEmpleadoOfHorariosCollectionHorarios = horariosCollectionHorarios.getIdFkEmpleado();
                horariosCollectionHorarios.setIdFkEmpleado(empleados);
                horariosCollectionHorarios = em.merge(horariosCollectionHorarios);
                if (oldIdFkEmpleadoOfHorariosCollectionHorarios != null) {
                    oldIdFkEmpleadoOfHorariosCollectionHorarios.getHorariosCollection().remove(horariosCollectionHorarios);
                    oldIdFkEmpleadoOfHorariosCollectionHorarios = em.merge(oldIdFkEmpleadoOfHorariosCollectionHorarios);
                }
            }
            for (Citas citasCollectionCitas : empleados.getCitasCollection()) {
                Empleados oldIdFkEmpleadoOfCitasCollectionCitas = citasCollectionCitas.getIdFkEmpleado();
                citasCollectionCitas.setIdFkEmpleado(empleados);
                citasCollectionCitas = em.merge(citasCollectionCitas);
                if (oldIdFkEmpleadoOfCitasCollectionCitas != null) {
                    oldIdFkEmpleadoOfCitasCollectionCitas.getCitasCollection().remove(citasCollectionCitas);
                    oldIdFkEmpleadoOfCitasCollectionCitas = em.merge(oldIdFkEmpleadoOfCitasCollectionCitas);
                }
            }
            for (Cotizaciones cotizacionesCollectionCotizaciones : empleados.getCotizacionesCollection()) {
                Empleados oldIdFkEmpleadoOfCotizacionesCollectionCotizaciones = cotizacionesCollectionCotizaciones.getIdFkEmpleado();
                cotizacionesCollectionCotizaciones.setIdFkEmpleado(empleados);
                cotizacionesCollectionCotizaciones = em.merge(cotizacionesCollectionCotizaciones);
                if (oldIdFkEmpleadoOfCotizacionesCollectionCotizaciones != null) {
                    oldIdFkEmpleadoOfCotizacionesCollectionCotizaciones.getCotizacionesCollection().remove(cotizacionesCollectionCotizaciones);
                    oldIdFkEmpleadoOfCotizacionesCollectionCotizaciones = em.merge(oldIdFkEmpleadoOfCotizacionesCollectionCotizaciones);
                }
            }
            for (Ventas ventasCollectionVentas : empleados.getVentasCollection()) {
                Empleados oldIdFkEmpleadoOfVentasCollectionVentas = ventasCollectionVentas.getIdFkEmpleado();
                ventasCollectionVentas.setIdFkEmpleado(empleados);
                ventasCollectionVentas = em.merge(ventasCollectionVentas);
                if (oldIdFkEmpleadoOfVentasCollectionVentas != null) {
                    oldIdFkEmpleadoOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
                    oldIdFkEmpleadoOfVentasCollectionVentas = em.merge(oldIdFkEmpleadoOfVentasCollectionVentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleados(empleados.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleados " + empleados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getIdEmpleado());
            Collection<Horarios> horariosCollectionOld = persistentEmpleados.getHorariosCollection();
            Collection<Horarios> horariosCollectionNew = empleados.getHorariosCollection();
            Collection<Citas> citasCollectionOld = persistentEmpleados.getCitasCollection();
            Collection<Citas> citasCollectionNew = empleados.getCitasCollection();
            Collection<Cotizaciones> cotizacionesCollectionOld = persistentEmpleados.getCotizacionesCollection();
            Collection<Cotizaciones> cotizacionesCollectionNew = empleados.getCotizacionesCollection();
            Collection<Ventas> ventasCollectionOld = persistentEmpleados.getVentasCollection();
            Collection<Ventas> ventasCollectionNew = empleados.getVentasCollection();
            List<String> illegalOrphanMessages = null;
            for (Horarios horariosCollectionOldHorarios : horariosCollectionOld) {
                if (!horariosCollectionNew.contains(horariosCollectionOldHorarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horarios " + horariosCollectionOldHorarios + " since its idFkEmpleado field is not nullable.");
                }
            }
            for (Citas citasCollectionOldCitas : citasCollectionOld) {
                if (!citasCollectionNew.contains(citasCollectionOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasCollectionOldCitas + " since its idFkEmpleado field is not nullable.");
                }
            }
            for (Cotizaciones cotizacionesCollectionOldCotizaciones : cotizacionesCollectionOld) {
                if (!cotizacionesCollectionNew.contains(cotizacionesCollectionOldCotizaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cotizaciones " + cotizacionesCollectionOldCotizaciones + " since its idFkEmpleado field is not nullable.");
                }
            }
            for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
                if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasCollectionOldVentas + " since its idFkEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Horarios> attachedHorariosCollectionNew = new ArrayList<Horarios>();
            for (Horarios horariosCollectionNewHorariosToAttach : horariosCollectionNew) {
                horariosCollectionNewHorariosToAttach = em.getReference(horariosCollectionNewHorariosToAttach.getClass(), horariosCollectionNewHorariosToAttach.getIdHorario());
                attachedHorariosCollectionNew.add(horariosCollectionNewHorariosToAttach);
            }
            horariosCollectionNew = attachedHorariosCollectionNew;
            empleados.setHorariosCollection(horariosCollectionNew);
            Collection<Citas> attachedCitasCollectionNew = new ArrayList<Citas>();
            for (Citas citasCollectionNewCitasToAttach : citasCollectionNew) {
                citasCollectionNewCitasToAttach = em.getReference(citasCollectionNewCitasToAttach.getClass(), citasCollectionNewCitasToAttach.getIdCita());
                attachedCitasCollectionNew.add(citasCollectionNewCitasToAttach);
            }
            citasCollectionNew = attachedCitasCollectionNew;
            empleados.setCitasCollection(citasCollectionNew);
            Collection<Cotizaciones> attachedCotizacionesCollectionNew = new ArrayList<Cotizaciones>();
            for (Cotizaciones cotizacionesCollectionNewCotizacionesToAttach : cotizacionesCollectionNew) {
                cotizacionesCollectionNewCotizacionesToAttach = em.getReference(cotizacionesCollectionNewCotizacionesToAttach.getClass(), cotizacionesCollectionNewCotizacionesToAttach.getIdCotizacion());
                attachedCotizacionesCollectionNew.add(cotizacionesCollectionNewCotizacionesToAttach);
            }
            cotizacionesCollectionNew = attachedCotizacionesCollectionNew;
            empleados.setCotizacionesCollection(cotizacionesCollectionNew);
            Collection<Ventas> attachedVentasCollectionNew = new ArrayList<Ventas>();
            for (Ventas ventasCollectionNewVentasToAttach : ventasCollectionNew) {
                ventasCollectionNewVentasToAttach = em.getReference(ventasCollectionNewVentasToAttach.getClass(), ventasCollectionNewVentasToAttach.getIdVenta());
                attachedVentasCollectionNew.add(ventasCollectionNewVentasToAttach);
            }
            ventasCollectionNew = attachedVentasCollectionNew;
            empleados.setVentasCollection(ventasCollectionNew);
            empleados = em.merge(empleados);
            for (Horarios horariosCollectionNewHorarios : horariosCollectionNew) {
                if (!horariosCollectionOld.contains(horariosCollectionNewHorarios)) {
                    Empleados oldIdFkEmpleadoOfHorariosCollectionNewHorarios = horariosCollectionNewHorarios.getIdFkEmpleado();
                    horariosCollectionNewHorarios.setIdFkEmpleado(empleados);
                    horariosCollectionNewHorarios = em.merge(horariosCollectionNewHorarios);
                    if (oldIdFkEmpleadoOfHorariosCollectionNewHorarios != null && !oldIdFkEmpleadoOfHorariosCollectionNewHorarios.equals(empleados)) {
                        oldIdFkEmpleadoOfHorariosCollectionNewHorarios.getHorariosCollection().remove(horariosCollectionNewHorarios);
                        oldIdFkEmpleadoOfHorariosCollectionNewHorarios = em.merge(oldIdFkEmpleadoOfHorariosCollectionNewHorarios);
                    }
                }
            }
            for (Citas citasCollectionNewCitas : citasCollectionNew) {
                if (!citasCollectionOld.contains(citasCollectionNewCitas)) {
                    Empleados oldIdFkEmpleadoOfCitasCollectionNewCitas = citasCollectionNewCitas.getIdFkEmpleado();
                    citasCollectionNewCitas.setIdFkEmpleado(empleados);
                    citasCollectionNewCitas = em.merge(citasCollectionNewCitas);
                    if (oldIdFkEmpleadoOfCitasCollectionNewCitas != null && !oldIdFkEmpleadoOfCitasCollectionNewCitas.equals(empleados)) {
                        oldIdFkEmpleadoOfCitasCollectionNewCitas.getCitasCollection().remove(citasCollectionNewCitas);
                        oldIdFkEmpleadoOfCitasCollectionNewCitas = em.merge(oldIdFkEmpleadoOfCitasCollectionNewCitas);
                    }
                }
            }
            for (Cotizaciones cotizacionesCollectionNewCotizaciones : cotizacionesCollectionNew) {
                if (!cotizacionesCollectionOld.contains(cotizacionesCollectionNewCotizaciones)) {
                    Empleados oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones = cotizacionesCollectionNewCotizaciones.getIdFkEmpleado();
                    cotizacionesCollectionNewCotizaciones.setIdFkEmpleado(empleados);
                    cotizacionesCollectionNewCotizaciones = em.merge(cotizacionesCollectionNewCotizaciones);
                    if (oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones != null && !oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones.equals(empleados)) {
                        oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones.getCotizacionesCollection().remove(cotizacionesCollectionNewCotizaciones);
                        oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones = em.merge(oldIdFkEmpleadoOfCotizacionesCollectionNewCotizaciones);
                    }
                }
            }
            for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
                if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
                    Empleados oldIdFkEmpleadoOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdFkEmpleado();
                    ventasCollectionNewVentas.setIdFkEmpleado(empleados);
                    ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
                    if (oldIdFkEmpleadoOfVentasCollectionNewVentas != null && !oldIdFkEmpleadoOfVentasCollectionNewVentas.equals(empleados)) {
                        oldIdFkEmpleadoOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
                        oldIdFkEmpleadoOfVentasCollectionNewVentas = em.merge(oldIdFkEmpleadoOfVentasCollectionNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getIdEmpleado();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Horarios> horariosCollectionOrphanCheck = empleados.getHorariosCollection();
            for (Horarios horariosCollectionOrphanCheckHorarios : horariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Horarios " + horariosCollectionOrphanCheckHorarios + " in its horariosCollection field has a non-nullable idFkEmpleado field.");
            }
            Collection<Citas> citasCollectionOrphanCheck = empleados.getCitasCollection();
            for (Citas citasCollectionOrphanCheckCitas : citasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Citas " + citasCollectionOrphanCheckCitas + " in its citasCollection field has a non-nullable idFkEmpleado field.");
            }
            Collection<Cotizaciones> cotizacionesCollectionOrphanCheck = empleados.getCotizacionesCollection();
            for (Cotizaciones cotizacionesCollectionOrphanCheckCotizaciones : cotizacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Cotizaciones " + cotizacionesCollectionOrphanCheckCotizaciones + " in its cotizacionesCollection field has a non-nullable idFkEmpleado field.");
            }
            Collection<Ventas> ventasCollectionOrphanCheck = empleados.getVentasCollection();
            for (Ventas ventasCollectionOrphanCheckVentas : ventasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Ventas " + ventasCollectionOrphanCheckVentas + " in its ventasCollection field has a non-nullable idFkEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Empleados recuperarClave(String emailEmpleado) {
        EntityManager em = getEntityManager();
        try {
            Query qt = em.createQuery("SELECT e FROM Empleados e WHERE e.emailEmpleado =:emailEmpleado");
            qt.setParameter("emailEmpleado", emailEmpleado);
            return (Empleados) qt.getSingleResult();
        } catch (Exception e) {
            return new Empleados();
        }
    }

}