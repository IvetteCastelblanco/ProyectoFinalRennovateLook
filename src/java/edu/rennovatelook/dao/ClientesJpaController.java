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
import edu.rennovatelook.entity.Citas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Ofertas;
import edu.rennovatelook.entity.Cotizaciones;
import edu.rennovatelook.entity.Ventas;

/**
 *
 * @author usuario
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getCitasCollection() == null) {
            clientes.setCitasCollection(new ArrayList<Citas>());
        }
        if (clientes.getOfertasCollection() == null) {
            clientes.setOfertasCollection(new ArrayList<Ofertas>());
        }
        if (clientes.getCotizacionesCollection() == null) {
            clientes.setCotizacionesCollection(new ArrayList<Cotizaciones>());
        }
        if (clientes.getVentasCollection() == null) {
            clientes.setVentasCollection(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Citas> attachedCitasCollection = new ArrayList<Citas>();
            for (Citas citasCollectionCitasToAttach : clientes.getCitasCollection()) {
                citasCollectionCitasToAttach = em.getReference(citasCollectionCitasToAttach.getClass(), citasCollectionCitasToAttach.getIdCita());
                attachedCitasCollection.add(citasCollectionCitasToAttach);
            }
            clientes.setCitasCollection(attachedCitasCollection);
            Collection<Ofertas> attachedOfertasCollection = new ArrayList<Ofertas>();
            for (Ofertas ofertasCollectionOfertasToAttach : clientes.getOfertasCollection()) {
                ofertasCollectionOfertasToAttach = em.getReference(ofertasCollectionOfertasToAttach.getClass(), ofertasCollectionOfertasToAttach.getIdOferta());
                attachedOfertasCollection.add(ofertasCollectionOfertasToAttach);
            }
            clientes.setOfertasCollection(attachedOfertasCollection);
            Collection<Cotizaciones> attachedCotizacionesCollection = new ArrayList<Cotizaciones>();
            for (Cotizaciones cotizacionesCollectionCotizacionesToAttach : clientes.getCotizacionesCollection()) {
                cotizacionesCollectionCotizacionesToAttach = em.getReference(cotizacionesCollectionCotizacionesToAttach.getClass(), cotizacionesCollectionCotizacionesToAttach.getIdCotizacion());
                attachedCotizacionesCollection.add(cotizacionesCollectionCotizacionesToAttach);
            }
            clientes.setCotizacionesCollection(attachedCotizacionesCollection);
            Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
            for (Ventas ventasCollectionVentasToAttach : clientes.getVentasCollection()) {
                ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdVenta());
                attachedVentasCollection.add(ventasCollectionVentasToAttach);
            }
            clientes.setVentasCollection(attachedVentasCollection);
            em.persist(clientes);
            for (Citas citasCollectionCitas : clientes.getCitasCollection()) {
                Clientes oldIdFkClienteOfCitasCollectionCitas = citasCollectionCitas.getIdFkCliente();
                citasCollectionCitas.setIdFkCliente(clientes);
                citasCollectionCitas = em.merge(citasCollectionCitas);
                if (oldIdFkClienteOfCitasCollectionCitas != null) {
                    oldIdFkClienteOfCitasCollectionCitas.getCitasCollection().remove(citasCollectionCitas);
                    oldIdFkClienteOfCitasCollectionCitas = em.merge(oldIdFkClienteOfCitasCollectionCitas);
                }
            }
            for (Ofertas ofertasCollectionOfertas : clientes.getOfertasCollection()) {
                Clientes oldIdFkClienteOfOfertasCollectionOfertas = ofertasCollectionOfertas.getIdFkCliente();
                ofertasCollectionOfertas.setIdFkCliente(clientes);
                ofertasCollectionOfertas = em.merge(ofertasCollectionOfertas);
                if (oldIdFkClienteOfOfertasCollectionOfertas != null) {
                    oldIdFkClienteOfOfertasCollectionOfertas.getOfertasCollection().remove(ofertasCollectionOfertas);
                    oldIdFkClienteOfOfertasCollectionOfertas = em.merge(oldIdFkClienteOfOfertasCollectionOfertas);
                }
            }
            for (Cotizaciones cotizacionesCollectionCotizaciones : clientes.getCotizacionesCollection()) {
                Clientes oldIdFkClienteOfCotizacionesCollectionCotizaciones = cotizacionesCollectionCotizaciones.getIdFkCliente();
                cotizacionesCollectionCotizaciones.setIdFkCliente(clientes);
                cotizacionesCollectionCotizaciones = em.merge(cotizacionesCollectionCotizaciones);
                if (oldIdFkClienteOfCotizacionesCollectionCotizaciones != null) {
                    oldIdFkClienteOfCotizacionesCollectionCotizaciones.getCotizacionesCollection().remove(cotizacionesCollectionCotizaciones);
                    oldIdFkClienteOfCotizacionesCollectionCotizaciones = em.merge(oldIdFkClienteOfCotizacionesCollectionCotizaciones);
                }
            }
            for (Ventas ventasCollectionVentas : clientes.getVentasCollection()) {
                Clientes oldIdFkClienteOfVentasCollectionVentas = ventasCollectionVentas.getIdFkCliente();
                ventasCollectionVentas.setIdFkCliente(clientes);
                ventasCollectionVentas = em.merge(ventasCollectionVentas);
                if (oldIdFkClienteOfVentasCollectionVentas != null) {
                    oldIdFkClienteOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
                    oldIdFkClienteOfVentasCollectionVentas = em.merge(oldIdFkClienteOfVentasCollectionVentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getIdCliente()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdCliente());
            Collection<Citas> citasCollectionOld = persistentClientes.getCitasCollection();
            Collection<Citas> citasCollectionNew = clientes.getCitasCollection();
            Collection<Ofertas> ofertasCollectionOld = persistentClientes.getOfertasCollection();
            Collection<Ofertas> ofertasCollectionNew = clientes.getOfertasCollection();
            Collection<Cotizaciones> cotizacionesCollectionOld = persistentClientes.getCotizacionesCollection();
            Collection<Cotizaciones> cotizacionesCollectionNew = clientes.getCotizacionesCollection();
            Collection<Ventas> ventasCollectionOld = persistentClientes.getVentasCollection();
            Collection<Ventas> ventasCollectionNew = clientes.getVentasCollection();
            List<String> illegalOrphanMessages = null;
            for (Citas citasCollectionOldCitas : citasCollectionOld) {
                if (!citasCollectionNew.contains(citasCollectionOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasCollectionOldCitas + " since its idFkCliente field is not nullable.");
                }
            }
            for (Ofertas ofertasCollectionOldOfertas : ofertasCollectionOld) {
                if (!ofertasCollectionNew.contains(ofertasCollectionOldOfertas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ofertas " + ofertasCollectionOldOfertas + " since its idFkCliente field is not nullable.");
                }
            }
            for (Cotizaciones cotizacionesCollectionOldCotizaciones : cotizacionesCollectionOld) {
                if (!cotizacionesCollectionNew.contains(cotizacionesCollectionOldCotizaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cotizaciones " + cotizacionesCollectionOldCotizaciones + " since its idFkCliente field is not nullable.");
                }
            }
            for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
                if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasCollectionOldVentas + " since its idFkCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Citas> attachedCitasCollectionNew = new ArrayList<Citas>();
            for (Citas citasCollectionNewCitasToAttach : citasCollectionNew) {
                citasCollectionNewCitasToAttach = em.getReference(citasCollectionNewCitasToAttach.getClass(), citasCollectionNewCitasToAttach.getIdCita());
                attachedCitasCollectionNew.add(citasCollectionNewCitasToAttach);
            }
            citasCollectionNew = attachedCitasCollectionNew;
            clientes.setCitasCollection(citasCollectionNew);
            Collection<Ofertas> attachedOfertasCollectionNew = new ArrayList<Ofertas>();
            for (Ofertas ofertasCollectionNewOfertasToAttach : ofertasCollectionNew) {
                ofertasCollectionNewOfertasToAttach = em.getReference(ofertasCollectionNewOfertasToAttach.getClass(), ofertasCollectionNewOfertasToAttach.getIdOferta());
                attachedOfertasCollectionNew.add(ofertasCollectionNewOfertasToAttach);
            }
            ofertasCollectionNew = attachedOfertasCollectionNew;
            clientes.setOfertasCollection(ofertasCollectionNew);
            Collection<Cotizaciones> attachedCotizacionesCollectionNew = new ArrayList<Cotizaciones>();
            for (Cotizaciones cotizacionesCollectionNewCotizacionesToAttach : cotizacionesCollectionNew) {
                cotizacionesCollectionNewCotizacionesToAttach = em.getReference(cotizacionesCollectionNewCotizacionesToAttach.getClass(), cotizacionesCollectionNewCotizacionesToAttach.getIdCotizacion());
                attachedCotizacionesCollectionNew.add(cotizacionesCollectionNewCotizacionesToAttach);
            }
            cotizacionesCollectionNew = attachedCotizacionesCollectionNew;
            clientes.setCotizacionesCollection(cotizacionesCollectionNew);
            Collection<Ventas> attachedVentasCollectionNew = new ArrayList<Ventas>();
            for (Ventas ventasCollectionNewVentasToAttach : ventasCollectionNew) {
                ventasCollectionNewVentasToAttach = em.getReference(ventasCollectionNewVentasToAttach.getClass(), ventasCollectionNewVentasToAttach.getIdVenta());
                attachedVentasCollectionNew.add(ventasCollectionNewVentasToAttach);
            }
            ventasCollectionNew = attachedVentasCollectionNew;
            clientes.setVentasCollection(ventasCollectionNew);
            clientes = em.merge(clientes);
            for (Citas citasCollectionNewCitas : citasCollectionNew) {
                if (!citasCollectionOld.contains(citasCollectionNewCitas)) {
                    Clientes oldIdFkClienteOfCitasCollectionNewCitas = citasCollectionNewCitas.getIdFkCliente();
                    citasCollectionNewCitas.setIdFkCliente(clientes);
                    citasCollectionNewCitas = em.merge(citasCollectionNewCitas);
                    if (oldIdFkClienteOfCitasCollectionNewCitas != null && !oldIdFkClienteOfCitasCollectionNewCitas.equals(clientes)) {
                        oldIdFkClienteOfCitasCollectionNewCitas.getCitasCollection().remove(citasCollectionNewCitas);
                        oldIdFkClienteOfCitasCollectionNewCitas = em.merge(oldIdFkClienteOfCitasCollectionNewCitas);
                    }
                }
            }
            for (Ofertas ofertasCollectionNewOfertas : ofertasCollectionNew) {
                if (!ofertasCollectionOld.contains(ofertasCollectionNewOfertas)) {
                    Clientes oldIdFkClienteOfOfertasCollectionNewOfertas = ofertasCollectionNewOfertas.getIdFkCliente();
                    ofertasCollectionNewOfertas.setIdFkCliente(clientes);
                    ofertasCollectionNewOfertas = em.merge(ofertasCollectionNewOfertas);
                    if (oldIdFkClienteOfOfertasCollectionNewOfertas != null && !oldIdFkClienteOfOfertasCollectionNewOfertas.equals(clientes)) {
                        oldIdFkClienteOfOfertasCollectionNewOfertas.getOfertasCollection().remove(ofertasCollectionNewOfertas);
                        oldIdFkClienteOfOfertasCollectionNewOfertas = em.merge(oldIdFkClienteOfOfertasCollectionNewOfertas);
                    }
                }
            }
            for (Cotizaciones cotizacionesCollectionNewCotizaciones : cotizacionesCollectionNew) {
                if (!cotizacionesCollectionOld.contains(cotizacionesCollectionNewCotizaciones)) {
                    Clientes oldIdFkClienteOfCotizacionesCollectionNewCotizaciones = cotizacionesCollectionNewCotizaciones.getIdFkCliente();
                    cotizacionesCollectionNewCotizaciones.setIdFkCliente(clientes);
                    cotizacionesCollectionNewCotizaciones = em.merge(cotizacionesCollectionNewCotizaciones);
                    if (oldIdFkClienteOfCotizacionesCollectionNewCotizaciones != null && !oldIdFkClienteOfCotizacionesCollectionNewCotizaciones.equals(clientes)) {
                        oldIdFkClienteOfCotizacionesCollectionNewCotizaciones.getCotizacionesCollection().remove(cotizacionesCollectionNewCotizaciones);
                        oldIdFkClienteOfCotizacionesCollectionNewCotizaciones = em.merge(oldIdFkClienteOfCotizacionesCollectionNewCotizaciones);
                    }
                }
            }
            for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
                if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
                    Clientes oldIdFkClienteOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdFkCliente();
                    ventasCollectionNewVentas.setIdFkCliente(clientes);
                    ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
                    if (oldIdFkClienteOfVentasCollectionNewVentas != null && !oldIdFkClienteOfVentasCollectionNewVentas.equals(clientes)) {
                        oldIdFkClienteOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
                        oldIdFkClienteOfVentasCollectionNewVentas = em.merge(oldIdFkClienteOfVentasCollectionNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Citas> citasCollectionOrphanCheck = clientes.getCitasCollection();
            for (Citas citasCollectionOrphanCheckCitas : citasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Citas " + citasCollectionOrphanCheckCitas + " in its citasCollection field has a non-nullable idFkCliente field.");
            }
            Collection<Ofertas> ofertasCollectionOrphanCheck = clientes.getOfertasCollection();
            for (Ofertas ofertasCollectionOrphanCheckOfertas : ofertasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Ofertas " + ofertasCollectionOrphanCheckOfertas + " in its ofertasCollection field has a non-nullable idFkCliente field.");
            }
            Collection<Cotizaciones> cotizacionesCollectionOrphanCheck = clientes.getCotizacionesCollection();
            for (Cotizaciones cotizacionesCollectionOrphanCheckCotizaciones : cotizacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Cotizaciones " + cotizacionesCollectionOrphanCheckCotizaciones + " in its cotizacionesCollection field has a non-nullable idFkCliente field.");
            }
            Collection<Ventas> ventasCollectionOrphanCheck = clientes.getVentasCollection();
            for (Ventas ventasCollectionOrphanCheckVentas : ventasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Ventas " + ventasCollectionOrphanCheckVentas + " in its ventasCollection field has a non-nullable idFkCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
