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
import edu.rennovatelook.entity.Estilistas;

/**
 *
 * @author usuario
 */
public class EstilistasJpaController implements Serializable {

    public EstilistasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estilistas estilistas) throws PreexistingEntityException, Exception {
        if (estilistas.getHorariosCollection() == null) {
            estilistas.setHorariosCollection(new ArrayList<Horarios>());
        }
        if (estilistas.getCitasCollection() == null) {
            estilistas.setCitasCollection(new ArrayList<Citas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Horarios> attachedHorariosCollection = new ArrayList<Horarios>();
            for (Horarios horariosCollectionHorariosToAttach : estilistas.getHorariosCollection()) {
                horariosCollectionHorariosToAttach = em.getReference(horariosCollectionHorariosToAttach.getClass(), horariosCollectionHorariosToAttach.getIdHorario());
                attachedHorariosCollection.add(horariosCollectionHorariosToAttach);
            }
            estilistas.setHorariosCollection(attachedHorariosCollection);
            Collection<Citas> attachedCitasCollection = new ArrayList<Citas>();
            for (Citas citasCollectionCitasToAttach : estilistas.getCitasCollection()) {
                citasCollectionCitasToAttach = em.getReference(citasCollectionCitasToAttach.getClass(), citasCollectionCitasToAttach.getIdCita());
                attachedCitasCollection.add(citasCollectionCitasToAttach);
            }
            estilistas.setCitasCollection(attachedCitasCollection);
            em.persist(estilistas);
            for (Horarios horariosCollectionHorarios : estilistas.getHorariosCollection()) {
                Estilistas oldIdFkEstilistaOfHorariosCollectionHorarios = horariosCollectionHorarios.getIdFkEstilista();
                horariosCollectionHorarios.setIdFkEstilista(estilistas);
                horariosCollectionHorarios = em.merge(horariosCollectionHorarios);
                if (oldIdFkEstilistaOfHorariosCollectionHorarios != null) {
                    oldIdFkEstilistaOfHorariosCollectionHorarios.getHorariosCollection().remove(horariosCollectionHorarios);
                    oldIdFkEstilistaOfHorariosCollectionHorarios = em.merge(oldIdFkEstilistaOfHorariosCollectionHorarios);
                }
            }
            for (Citas citasCollectionCitas : estilistas.getCitasCollection()) {
                Estilistas oldIdFkEstilistaOfCitasCollectionCitas = citasCollectionCitas.getIdFkEstilista();
                citasCollectionCitas.setIdFkEstilista(estilistas);
                citasCollectionCitas = em.merge(citasCollectionCitas);
                if (oldIdFkEstilistaOfCitasCollectionCitas != null) {
                    oldIdFkEstilistaOfCitasCollectionCitas.getCitasCollection().remove(citasCollectionCitas);
                    oldIdFkEstilistaOfCitasCollectionCitas = em.merge(oldIdFkEstilistaOfCitasCollectionCitas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstilistas(estilistas.getIdEstilista()) != null) {
                throw new PreexistingEntityException("Estilistas " + estilistas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estilistas estilistas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estilistas persistentEstilistas = em.find(Estilistas.class, estilistas.getIdEstilista());
            Collection<Horarios> horariosCollectionOld = persistentEstilistas.getHorariosCollection();
            Collection<Horarios> horariosCollectionNew = estilistas.getHorariosCollection();
            Collection<Citas> citasCollectionOld = persistentEstilistas.getCitasCollection();
            Collection<Citas> citasCollectionNew = estilistas.getCitasCollection();
            List<String> illegalOrphanMessages = null;
            for (Horarios horariosCollectionOldHorarios : horariosCollectionOld) {
                if (!horariosCollectionNew.contains(horariosCollectionOldHorarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horarios " + horariosCollectionOldHorarios + " since its idFkEstilista field is not nullable.");
                }
            }
            for (Citas citasCollectionOldCitas : citasCollectionOld) {
                if (!citasCollectionNew.contains(citasCollectionOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasCollectionOldCitas + " since its idFkEstilista field is not nullable.");
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
            estilistas.setHorariosCollection(horariosCollectionNew);
            Collection<Citas> attachedCitasCollectionNew = new ArrayList<Citas>();
            for (Citas citasCollectionNewCitasToAttach : citasCollectionNew) {
                citasCollectionNewCitasToAttach = em.getReference(citasCollectionNewCitasToAttach.getClass(), citasCollectionNewCitasToAttach.getIdCita());
                attachedCitasCollectionNew.add(citasCollectionNewCitasToAttach);
            }
            citasCollectionNew = attachedCitasCollectionNew;
            estilistas.setCitasCollection(citasCollectionNew);
            estilistas = em.merge(estilistas);
            for (Horarios horariosCollectionNewHorarios : horariosCollectionNew) {
                if (!horariosCollectionOld.contains(horariosCollectionNewHorarios)) {
                    Estilistas oldIdFkEstilistaOfHorariosCollectionNewHorarios = horariosCollectionNewHorarios.getIdFkEstilista();
                    horariosCollectionNewHorarios.setIdFkEstilista(estilistas);
                    horariosCollectionNewHorarios = em.merge(horariosCollectionNewHorarios);
                    if (oldIdFkEstilistaOfHorariosCollectionNewHorarios != null && !oldIdFkEstilistaOfHorariosCollectionNewHorarios.equals(estilistas)) {
                        oldIdFkEstilistaOfHorariosCollectionNewHorarios.getHorariosCollection().remove(horariosCollectionNewHorarios);
                        oldIdFkEstilistaOfHorariosCollectionNewHorarios = em.merge(oldIdFkEstilistaOfHorariosCollectionNewHorarios);
                    }
                }
            }
            for (Citas citasCollectionNewCitas : citasCollectionNew) {
                if (!citasCollectionOld.contains(citasCollectionNewCitas)) {
                    Estilistas oldIdFkEstilistaOfCitasCollectionNewCitas = citasCollectionNewCitas.getIdFkEstilista();
                    citasCollectionNewCitas.setIdFkEstilista(estilistas);
                    citasCollectionNewCitas = em.merge(citasCollectionNewCitas);
                    if (oldIdFkEstilistaOfCitasCollectionNewCitas != null && !oldIdFkEstilistaOfCitasCollectionNewCitas.equals(estilistas)) {
                        oldIdFkEstilistaOfCitasCollectionNewCitas.getCitasCollection().remove(citasCollectionNewCitas);
                        oldIdFkEstilistaOfCitasCollectionNewCitas = em.merge(oldIdFkEstilistaOfCitasCollectionNewCitas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estilistas.getIdEstilista();
                if (findEstilistas(id) == null) {
                    throw new NonexistentEntityException("The estilistas with id " + id + " no longer exists.");
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
            Estilistas estilistas;
            try {
                estilistas = em.getReference(Estilistas.class, id);
                estilistas.getIdEstilista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estilistas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Horarios> horariosCollectionOrphanCheck = estilistas.getHorariosCollection();
            for (Horarios horariosCollectionOrphanCheckHorarios : horariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estilistas (" + estilistas + ") cannot be destroyed since the Horarios " + horariosCollectionOrphanCheckHorarios + " in its horariosCollection field has a non-nullable idFkEstilista field.");
            }
            Collection<Citas> citasCollectionOrphanCheck = estilistas.getCitasCollection();
            for (Citas citasCollectionOrphanCheckCitas : citasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estilistas (" + estilistas + ") cannot be destroyed since the Citas " + citasCollectionOrphanCheckCitas + " in its citasCollection field has a non-nullable idFkEstilista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estilistas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estilistas> findEstilistasEntities() {
        return findEstilistasEntities(true, -1, -1);
    }

    public List<Estilistas> findEstilistasEntities(int maxResults, int firstResult) {
        return findEstilistasEntities(false, maxResults, firstResult);
    }

    private List<Estilistas> findEstilistasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estilistas.class));
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

    public Estilistas findEstilistas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estilistas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstilistasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estilistas> rt = cq.from(Estilistas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
