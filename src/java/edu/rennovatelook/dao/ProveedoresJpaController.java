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
import edu.rennovatelook.entity.Productos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import edu.rennovatelook.entity.Proveedores;

/**
 *
 * @author usuario
 */
public class ProveedoresJpaController implements Serializable {

    public ProveedoresJpaController() {
        this.emf = Persistence.createEntityManagerFactory("RennovateLooksPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) throws PreexistingEntityException, Exception {
        if (proveedores.getProductosCollection() == null) {
            proveedores.setProductosCollection(new ArrayList<Productos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : proveedores.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getIdProducto());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            proveedores.setProductosCollection(attachedProductosCollection);
            em.persist(proveedores);
            for (Productos productosCollectionProductos : proveedores.getProductosCollection()) {
                Proveedores oldIdFkProveedorOfProductosCollectionProductos = productosCollectionProductos.getIdFkProveedor();
                productosCollectionProductos.setIdFkProveedor(proveedores);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldIdFkProveedorOfProductosCollectionProductos != null) {
                    oldIdFkProveedorOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldIdFkProveedorOfProductosCollectionProductos = em.merge(oldIdFkProveedorOfProductosCollectionProductos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedores(proveedores.getIdProveedor()) != null) {
                throw new PreexistingEntityException("Proveedores " + proveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getIdProveedor());
            Collection<Productos> productosCollectionOld = persistentProveedores.getProductosCollection();
            Collection<Productos> productosCollectionNew = proveedores.getProductosCollection();
            List<String> illegalOrphanMessages = null;
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productos " + productosCollectionOldProductos + " since its idFkProveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIdProducto());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            proveedores.setProductosCollection(productosCollectionNew);
            proveedores = em.merge(proveedores);
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Proveedores oldIdFkProveedorOfProductosCollectionNewProductos = productosCollectionNewProductos.getIdFkProveedor();
                    productosCollectionNewProductos.setIdFkProveedor(proveedores);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldIdFkProveedorOfProductosCollectionNewProductos != null && !oldIdFkProveedorOfProductosCollectionNewProductos.equals(proveedores)) {
                        oldIdFkProveedorOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldIdFkProveedorOfProductosCollectionNewProductos = em.merge(oldIdFkProveedorOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedores.getIdProveedor();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
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
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getIdProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Productos> productosCollectionOrphanCheck = proveedores.getProductosCollection();
            for (Productos productosCollectionOrphanCheckProductos : productosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedores (" + proveedores + ") cannot be destroyed since the Productos " + productosCollectionOrphanCheckProductos + " in its productosCollection field has a non-nullable idFkProveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedores.class));
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

    public Proveedores findProveedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedores> rt = cq.from(Proveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
