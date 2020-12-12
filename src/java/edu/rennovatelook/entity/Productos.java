/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p")
    , @NamedQuery(name = "Productos.findByIdProducto", query = "SELECT p FROM Productos p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Productos.findByNombreProducto", query = "SELECT p FROM Productos p WHERE p.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "Productos.findByCantidadProducto", query = "SELECT p FROM Productos p WHERE p.cantidadProducto = :cantidadProducto")
    , @NamedQuery(name = "Productos.findByPrecioCompraProducto", query = "SELECT p FROM Productos p WHERE p.precioCompraProducto = :precioCompraProducto")
    , @NamedQuery(name = "Productos.findByPrecioVentaProducto", query = "SELECT p FROM Productos p WHERE p.precioVentaProducto = :precioVentaProducto")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto")
    private Integer idProducto;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "cantidad_producto")
    private String cantidadProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_compra_producto")
    private Float precioCompraProducto;
    @Column(name = "precio_venta_producto")
    private Float precioVentaProducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkProducto", fetch = FetchType.LAZY)
    private Collection<Citas> citasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkProducto", fetch = FetchType.LAZY)
    private Collection<OrdenCompras> ordenComprasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkProducto", fetch = FetchType.LAZY)
    private Collection<Ventas> ventasCollection;
    @JoinColumn(name = "id_fk_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedores idFkProveedor;

    public Productos() {
    }

    public Productos(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Float getPrecioCompraProducto() {
        return precioCompraProducto;
    }

    public void setPrecioCompraProducto(Float precioCompraProducto) {
        this.precioCompraProducto = precioCompraProducto;
    }

    public Float getPrecioVentaProducto() {
        return precioVentaProducto;
    }

    public void setPrecioVentaProducto(Float precioVentaProducto) {
        this.precioVentaProducto = precioVentaProducto;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
    }

    @XmlTransient
    public Collection<OrdenCompras> getOrdenComprasCollection() {
        return ordenComprasCollection;
    }

    public void setOrdenComprasCollection(Collection<OrdenCompras> ordenComprasCollection) {
        this.ordenComprasCollection = ordenComprasCollection;
    }

    @XmlTransient
    public Collection<Ventas> getVentasCollection() {
        return ventasCollection;
    }

    public void setVentasCollection(Collection<Ventas> ventasCollection) {
        this.ventasCollection = ventasCollection;
    }

    public Proveedores getIdFkProveedor() {
        return idFkProveedor;
    }

    public void setIdFkProveedor(Proveedores idFkProveedor) {
        this.idFkProveedor = idFkProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Productos[ idProducto=" + idProducto + " ]";
    }
    
}
