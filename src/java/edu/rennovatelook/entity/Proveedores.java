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
import javax.persistence.Id;
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
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p")
    , @NamedQuery(name = "Proveedores.findByIdProveedor", query = "SELECT p FROM Proveedores p WHERE p.idProveedor = :idProveedor")
    , @NamedQuery(name = "Proveedores.findByNombresProveedor", query = "SELECT p FROM Proveedores p WHERE p.nombresProveedor = :nombresProveedor")
    , @NamedQuery(name = "Proveedores.findByApellidosProveedor", query = "SELECT p FROM Proveedores p WHERE p.apellidosProveedor = :apellidosProveedor")
    , @NamedQuery(name = "Proveedores.findByTelefonoProveedor", query = "SELECT p FROM Proveedores p WHERE p.telefonoProveedor = :telefonoProveedor")
    , @NamedQuery(name = "Proveedores.findByEmailProveedor", query = "SELECT p FROM Proveedores p WHERE p.emailProveedor = :emailProveedor")
    , @NamedQuery(name = "Proveedores.findByNombreEmpresaProveedor", query = "SELECT p FROM Proveedores p WHERE p.nombreEmpresaProveedor = :nombreEmpresaProveedor")
    , @NamedQuery(name = "Proveedores.findByTelefonoEmpresaProveedor", query = "SELECT p FROM Proveedores p WHERE p.telefonoEmpresaProveedor = :telefonoEmpresaProveedor")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    @Column(name = "nombres_proveedor")
    private String nombresProveedor;
    @Column(name = "apellidos_proveedor")
    private String apellidosProveedor;
    @Column(name = "telefono_proveedor")
    private Integer telefonoProveedor;
    @Column(name = "email_proveedor")
    private String emailProveedor;
    @Column(name = "nombre_empresa_proveedor")
    private String nombreEmpresaProveedor;
    @Column(name = "telefono_empresa_proveedor")
    private Integer telefonoEmpresaProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkProveedor", fetch = FetchType.LAZY)
    private Collection<Productos> productosCollection;

    public Proveedores() {
    }

    public Proveedores(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombresProveedor() {
        return nombresProveedor;
    }

    public void setNombresProveedor(String nombresProveedor) {
        this.nombresProveedor = nombresProveedor;
    }

    public String getApellidosProveedor() {
        return apellidosProveedor;
    }

    public void setApellidosProveedor(String apellidosProveedor) {
        this.apellidosProveedor = apellidosProveedor;
    }

    public Integer getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(Integer telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getNombreEmpresaProveedor() {
        return nombreEmpresaProveedor;
    }

    public void setNombreEmpresaProveedor(String nombreEmpresaProveedor) {
        this.nombreEmpresaProveedor = nombreEmpresaProveedor;
    }

    public Integer getTelefonoEmpresaProveedor() {
        return telefonoEmpresaProveedor;
    }

    public void setTelefonoEmpresaProveedor(Integer telefonoEmpresaProveedor) {
        this.telefonoEmpresaProveedor = telefonoEmpresaProveedor;
    }

    @XmlTransient
    public Collection<Productos> getProductosCollection() {
        return productosCollection;
    }

    public void setProductosCollection(Collection<Productos> productosCollection) {
        this.productosCollection = productosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Proveedores[ idProveedor=" + idProveedor + " ]";
    }
    
}
