/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.dao;

import edu.rennovatelook.dao.exceptions.IllegalOrphanException;
import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.rennovatelook.entity.Proveedores;
import edu.rennovatelook.entity.Citas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import edu.rennovatelook.entity.OrdenCompras;
import edu.rennovatelook.entity.Productos;
import edu.rennovatelook.entity.Ventas;

/**
 *
 * @author usuario
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) {
        if (productos.getCitasCollection() == null) {
            productos.setCitasCollection(new ArrayList<Citas>());
        }
        if (productos.getOrdenComprasCollection() == null) {
            productos.setOrdenComprasCollection(new ArrayList<OrdenCompras>());
        }
        if (productos.getVentasCollection() == null) {
            productos.setVentasCollection(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores idFkProveedor = productos.getIdFkProveedor();
            if (idFkProveedor != null) {
                idFkProveedor = em.getReference(idFkProveedor.getClass(), idFkProveedor.getIdProveedor());
                productos.setIdFkProveedor(idFkProveedor);
            }
            Collection<Citas> attachedCitasCollection = new ArrayList<Citas>();
            for (Citas citasCollectionCitasToAttach : productos.getCitasCollection()) {
                citasCollectionCitasToAttach = em.getReference(citasCollectionCitasToAttach.getClass(), citasCollectionCitasToAttach.getIdCita());
                attachedCitasCollection.add(citasCollectionCitasToAttach);
            }
            productos.setCitasCollection(attachedCitasCollection);
            Collection<OrdenCompras> attachedOrdenComprasCollection = new ArrayList<OrdenCompras>();
            for (OrdenCompras ordenComprasCollectionOrdenComprasToAttach : productos.getOrdenComprasCollection()) {
                ordenComprasCollectionOrdenComprasToAttach = em.getReference(ordenComprasCollectionOrdenComprasToAttach.getClass(), ordenComprasCollectionOrdenComprasToAttach.getIdOrdenCompra());
                attachedOrdenComprasCollection.add(ordenComprasCollectionOrdenComprasToAttach);
            }
            productos.setOrdenComprasCollection(attachedOrdenComprasCollection);
            Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
            for (Ventas ventasCollectionVentasToAttach : productos.getVentasCollection()) {
                ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdVenta());
                attachedVentasCollection.add(ventasCollectionVentasToAttach);
            }
            productos.setVentasCollection(attachedVentasCollection);
            em.persist(productos);
            if (idFkProveedor != null) {
                idFkProveedor.getProductosCollection().add(productos);
                idFkProveedor = em.merge(idFkProveedor);
            }
            for (Citas citasCollectionCitas : productos.getCitasCollection()) {
                Productos oldIdFkProductoOfCitasCollectionCitas = citasCollectionCitas.getIdFkProducto();
                citasCollectionCitas.setIdFkProducto(productos);
                citasCollectionCitas = em.merge(citasCollectionCitas);
                if (oldIdFkProductoOfCitasCollectionCitas != null) {
                    oldIdFkProductoOfCitasCollectionCitas.getCitasCollection().remove(citasCollectionCitas);
                    oldIdFkProductoOfCitasCollectionCitas = em.merge(oldIdFkProductoOfCitasCollectionCitas);
                }
            }
            for (OrdenCompras ordenComprasCollectionOrdenCompras : productos.getOrdenComprasCollection()) {
                Productos oldIdFkProductoOfOrdenComprasCollectionOrdenCompras = ordenComprasCollectionOrdenCompras.getIdFkProducto();
                ordenComprasCollectionOrdenCompras.setIdFkProducto(productos);
                ordenComprasCollectionOrdenCompras = em.merge(ordenComprasCollectionOrdenCompras);
                if (oldIdFkProductoOfOrdenComprasCollectionOrdenCompras != null) {
                    oldIdFkProductoOfOrdenComprasCollectionOrdenCompras.getOrdenComprasCollection().remove(ordenComprasCollectionOrdenCompras);
                    oldIdFkProductoOfOrdenComprasCollectionOrdenCompras = em.merge(oldIdFkProductoOfOrdenComprasCollectionOrdenCompras);
                }
            }
            for (Ventas ventasCollectionVentas : productos.getVentasCollection()) {
                Productos oldIdFkProductoOfVentasCollectionVentas = ventasCollectionVentas.getIdFkProducto();
                ventasCollectionVentas.setIdFkProducto(productos);
                ventasCollectionVentas = em.merge(ventasCollectionVentas);
                if (oldIdFkProductoOfVentasCollectionVentas != null) {
                    oldIdFkProductoOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
                    oldIdFkProductoOfVentasCollectionVentas = em.merge(oldIdFkProductoOfVentasCollectionVentas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getIdProducto());
            Proveedores idFkProveedorOld = persistentProductos.getIdFkProveedor();
            Proveedores idFkProveedorNew = productos.getIdFkProveedor();
            Collection<Citas> citasCollectionOld = persistentProductos.getCitasCollection();
            Collection<Citas> citasCollectionNew = productos.getCitasCollection();
            Collection<OrdenCompras> ordenComprasCollectionOld = persistentProductos.getOrdenComprasCollection();
            Collection<OrdenCompras> ordenComprasCollectionNew = productos.getOrdenComprasCollection();
            Collection<Ventas> ventasCollectionOld = persistentProductos.getVentasCollection();
            Collection<Ventas> ventasCollectionNew = productos.getVentasCollection();
            List<String> illegalOrphanMessages = null;
            for (Citas citasCollectionOldCitas : citasCollectionOld) {
                if (!citasCollectionNew.contains(citasCollectionOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasCollectionOldCitas + " since its idFkProducto field is not nullable.");
                }
            }
            for (OrdenCompras ordenComprasCollectionOldOrdenCompras : ordenComprasCollectionOld) {
                if (!ordenComprasCollectionNew.contains(ordenComprasCollectionOldOrdenCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrdenCompras " + ordenComprasCollectionOldOrdenCompras + " since its idFkProducto field is not nullable.");
                }
            }
            for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
                if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasCollectionOldVentas + " since its idFkProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFkProveedorNew != null) {
                idFkProveedorNew = em.getReference(idFkProveedorNew.getClass(), idFkProveedorNew.getIdProveedor());
                productos.setIdFkProveedor(idFkProveedorNew);
            }
            Collection<Citas> attachedCitasCollectionNew = new ArrayList<Citas>();
            for (Citas citasCollectionNewCitasToAttach : citasCollectionNew) {
                citasCollectionNewCitasToAttach = em.getReference(citasCollectionNewCitasToAttach.getClass(), citasCollectionNewCitasToAttach.getIdCita());
                attachedCitasCollectionNew.add(citasCollectionNewCitasToAttach);
            }
            citasCollectionNew = attachedCitasCollectionNew;
            productos.setCitasCollection(citasCollectionNew);
            Collection<OrdenCompras> attachedOrdenComprasCollectionNew = new ArrayList<OrdenCompras>();
            for (OrdenCompras ordenComprasCollectionNewOrdenComprasToAttach : ordenComprasCollectionNew) {
                ordenComprasCollectionNewOrdenComprasToAttach = em.getReference(ordenComprasCollectionNewOrdenComprasToAttach.getClass(), ordenComprasCollectionNewOrdenComprasToAttach.getIdOrdenCompra());
                attachedOrdenComprasCollectionNew.add(ordenComprasCollectionNewOrdenComprasToAttach);
            }
            ordenComprasCollectionNew = attachedOrdenComprasCollectionNew;
            productos.setOrdenComprasCollection(ordenComprasCollectionNew);
            Collection<Ventas> attachedVentasCollectionNew = new ArrayList<Ventas>();
            for (Ventas ventasCollectionNewVentasToAttach : ventasCollectionNew) {
                ventasCollectionNewVentasToAttach = em.getReference(ventasCollectionNewVentasToAttach.getClass(), ventasCollectionNewVentasToAttach.getIdVenta());
                attachedVentasCollectionNew.add(ventasCollectionNewVentasToAttach);
            }
            ventasCollectionNew = attachedVentasCollectionNew;
            productos.setVentasCollection(ventasCollectionNew);
            productos = em.merge(productos);
            if (idFkProveedorOld != null && !idFkProveedorOld.equals(idFkProveedorNew)) {
                idFkProveedorOld.getProductosCollection().remove(productos);
                idFkProveedorOld = em.merge(idFkProveedorOld);
            }
            if (idFkProveedorNew != null && !idFkProveedorNew.equals(idFkProveedorOld)) {
                idFkProveedorNew.getProductosCollection().add(productos);
                idFkProveedorNew = em.merge(idFkProveedorNew);
            }
            for (Citas citasCollectionNewCitas : citasCollectionNew) {
                if (!citasCollectionOld.contains(citasCollectionNewCitas)) {
                    Productos oldIdFkProductoOfCitasCollectionNewCitas = citasCollectionNewCitas.getIdFkProducto();
                    citasCollectionNewCitas.setIdFkProducto(productos);
                    citasCollectionNewCitas = em.merge(citasCollectionNewCitas);
                    if (oldIdFkProductoOfCitasCollectionNewCitas != null && !oldIdFkProductoOfCitasCollectionNewCitas.equals(productos)) {
                        oldIdFkProductoOfCitasCollectionNewCitas.getCitasCollection().remove(citasCollectionNewCitas);
                        oldIdFkProductoOfCitasCollectionNewCitas = em.merge(oldIdFkProductoOfCitasCollectionNewCitas);
                    }
                }
            }
            for (OrdenCompras ordenComprasCollectionNewOrdenCompras : ordenComprasCollectionNew) {
                if (!ordenComprasCollectionOld.contains(ordenComprasCollectionNewOrdenCompras)) {
                    Productos oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras = ordenComprasCollectionNewOrdenCompras.getIdFkProducto();
                    ordenComprasCollectionNewOrdenCompras.setIdFkProducto(productos);
                    ordenComprasCollectionNewOrdenCompras = em.merge(ordenComprasCollectionNewOrdenCompras);
                    if (oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras != null && !oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras.equals(productos)) {
                        oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras.getOrdenComprasCollection().remove(ordenComprasCollectionNewOrdenCompras);
                        oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras = em.merge(oldIdFkProductoOfOrdenComprasCollectionNewOrdenCompras);
                    }
                }
            }
            for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
                if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
                    Productos oldIdFkProductoOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdFkProducto();
                    ventasCollectionNewVentas.setIdFkProducto(productos);
                    ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
                    if (oldIdFkProductoOfVentasCollectionNewVentas != null && !oldIdFkProductoOfVentasCollectionNewVentas.equals(productos)) {
                        oldIdFkProductoOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
                        oldIdFkProductoOfVentasCollectionNewVentas = em.merge(oldIdFkProductoOfVentasCollectionNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getIdProducto();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Citas> citasCollectionOrphanCheck = productos.getCitasCollection();
            for (Citas citasCollectionOrphanCheckCitas : citasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the Citas " + citasCollectionOrphanCheckCitas + " in its citasCollection field has a non-nullable idFkProducto field.");
            }
            Collection<OrdenCompras> ordenComprasCollectionOrphanCheck = productos.getOrdenComprasCollection();
            for (OrdenCompras ordenComprasCollectionOrphanCheckOrdenCompras : ordenComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the OrdenCompras " + ordenComprasCollectionOrphanCheckOrdenCompras + " in its ordenComprasCollection field has a non-nullable idFkProducto field.");
            }
            Collection<Ventas> ventasCollectionOrphanCheck = productos.getVentasCollection();
            for (Ventas ventasCollectionOrphanCheckVentas : ventasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the Ventas " + ventasCollectionOrphanCheckVentas + " in its ventasCollection field has a non-nullable idFkProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedores idFkProveedor = productos.getIdFkProveedor();
            if (idFkProveedor != null) {
                idFkProveedor.getProductosCollection().remove(productos);
                idFkProveedor = em.merge(idFkProveedor);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
