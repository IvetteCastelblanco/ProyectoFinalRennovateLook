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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "Clientes.findByNombresCliente", query = "SELECT c FROM Clientes c WHERE c.nombresCliente = :nombresCliente")
    , @NamedQuery(name = "Clientes.findByApellidosCliente", query = "SELECT c FROM Clientes c WHERE c.apellidosCliente = :apellidosCliente")
    , @NamedQuery(name = "Clientes.findByTelefonoCliente", query = "SELECT c FROM Clientes c WHERE c.telefonoCliente = :telefonoCliente")
    , @NamedQuery(name = "Clientes.findByDireccionCliente", query = "SELECT c FROM Clientes c WHERE c.direccionCliente = :direccionCliente")
    , @NamedQuery(name = "Clientes.findByEmailCliente", query = "SELECT c FROM Clientes c WHERE c.emailCliente = :emailCliente")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "nombres_cliente")
    private String nombresCliente;
    @Column(name = "apellidos_cliente")
    private String apellidosCliente;
    @Column(name = "telefono_cliente")
    private Integer telefonoCliente;
    @Column(name = "direccion_cliente")
    private String direccionCliente;
    @Column(name = "email_cliente")
    private String emailCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkCliente", fetch = FetchType.LAZY)
    private Collection<Citas> citasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkCliente", fetch = FetchType.LAZY)
    private Collection<Ofertas> ofertasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkCliente", fetch = FetchType.LAZY)
    private Collection<Cotizaciones> cotizacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkCliente", fetch = FetchType.LAZY)
    private Collection<Ventas> ventasCollection;

    public Clientes() {
    }

    public Clientes(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public Integer getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(Integer telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
    }

    @XmlTransient
    public Collection<Ofertas> getOfertasCollection() {
        return ofertasCollection;
    }

    public void setOfertasCollection(Collection<Ofertas> ofertasCollection) {
        this.ofertasCollection = ofertasCollection;
    }

    @XmlTransient
    public Collection<Cotizaciones> getCotizacionesCollection() {
        return cotizacionesCollection;
    }

    public void setCotizacionesCollection(Collection<Cotizaciones> cotizacionesCollection) {
        this.cotizacionesCollection = cotizacionesCollection;
    }

    @XmlTransient
    public Collection<Ventas> getVentasCollection() {
        return ventasCollection;
    }

    public void setVentasCollection(Collection<Ventas> ventasCollection) {
        this.ventasCollection = ventasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Clientes[ idCliente=" + idCliente + " ]";
    }
    
}
