/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rennovatelook.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "orden_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCompras.findAll", query = "SELECT o FROM OrdenCompras o")
    , @NamedQuery(name = "OrdenCompras.findByIdOrdenCompra", query = "SELECT o FROM OrdenCompras o WHERE o.idOrdenCompra = :idOrdenCompra")
    , @NamedQuery(name = "OrdenCompras.findByFechaOrdenCompra", query = "SELECT o FROM OrdenCompras o WHERE o.fechaOrdenCompra = :fechaOrdenCompra")
    , @NamedQuery(name = "OrdenCompras.findByCantidadOrdenCompra", query = "SELECT o FROM OrdenCompras o WHERE o.cantidadOrdenCompra = :cantidadOrdenCompra")
    , @NamedQuery(name = "OrdenCompras.findByTotalOrdenCompra", query = "SELECT o FROM OrdenCompras o WHERE o.totalOrdenCompra = :totalOrdenCompra")})
public class OrdenCompras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_compra")
    private Integer idOrdenCompra;
    @Column(name = "fecha_orden_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrdenCompra;
    @Column(name = "cantidad_orden_compra")
    private Integer cantidadOrdenCompra;
    @Column(name = "total_orden_compra")
    private Integer totalOrdenCompra;
    @JoinColumn(name = "id_fk_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos idFkProducto;

    public OrdenCompras() {
    }

    public OrdenCompras(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Date getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(Date fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public Integer getCantidadOrdenCompra() {
        return cantidadOrdenCompra;
    }

    public void setCantidadOrdenCompra(Integer cantidadOrdenCompra) {
        this.cantidadOrdenCompra = cantidadOrdenCompra;
    }

    public Integer getTotalOrdenCompra() {
        return totalOrdenCompra;
    }

    public void setTotalOrdenCompra(Integer totalOrdenCompra) {
        this.totalOrdenCompra = totalOrdenCompra;
    }

    public Productos getIdFkProducto() {
        return idFkProducto;
    }

    public void setIdFkProducto(Productos idFkProducto) {
        this.idFkProducto = idFkProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenCompra != null ? idOrdenCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenCompras)) {
            return false;
        }
        OrdenCompras other = (OrdenCompras) object;
        if ((this.idOrdenCompra == null && other.idOrdenCompra != null) || (this.idOrdenCompra != null && !this.idOrdenCompra.equals(other.idOrdenCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.OrdenCompras[ idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
