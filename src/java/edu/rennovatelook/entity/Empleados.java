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
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByIdEmpleado", query = "SELECT e FROM Empleados e WHERE e.idEmpleado = :idEmpleado")
    , @NamedQuery(name = "Empleados.findByNombresEmpleado", query = "SELECT e FROM Empleados e WHERE e.nombresEmpleado = :nombresEmpleado")
    , @NamedQuery(name = "Empleados.findByApellidosEmpleado", query = "SELECT e FROM Empleados e WHERE e.apellidosEmpleado = :apellidosEmpleado")
    , @NamedQuery(name = "Empleados.findByTelefonoEmpleado", query = "SELECT e FROM Empleados e WHERE e.telefonoEmpleado = :telefonoEmpleado")
    , @NamedQuery(name = "Empleados.findByDireccionEmpleado", query = "SELECT e FROM Empleados e WHERE e.direccionEmpleado = :direccionEmpleado")
    , @NamedQuery(name = "Empleados.findByEmailEmpleado", query = "SELECT e FROM Empleados e WHERE e.emailEmpleado = :emailEmpleado")
    , @NamedQuery(name = "Empleados.findByCargoEmpleado", query = "SELECT e FROM Empleados e WHERE e.cargoEmpleado = :cargoEmpleado")
    , @NamedQuery(name = "Empleados.findByClaveEmpleado", query = "SELECT e FROM Empleados e WHERE e.claveEmpleado = :claveEmpleado")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "nombres_empleado")
    private String nombresEmpleado;
    @Column(name = "apellidos_empleado")
    private String apellidosEmpleado;
    @Column(name = "telefono_empleado")
    private Integer telefonoEmpleado;
    @Column(name = "direccion_empleado")
    private String direccionEmpleado;
    @Column(name = "email_empleado")
    private String emailEmpleado;
    @Column(name = "cargo_empleado")
    private String cargoEmpleado;
    @Column(name = "clave_empleado")
    private String claveEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEmpleado", fetch = FetchType.LAZY)
    private Collection<Horarios> horariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEmpleado", fetch = FetchType.LAZY)
    private Collection<Citas> citasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEmpleado", fetch = FetchType.LAZY)
    private Collection<Cotizaciones> cotizacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEmpleado", fetch = FetchType.LAZY)
    private Collection<Ventas> ventasCollection;

    public Empleados() {
    }

    public Empleados(Integer idEmpleado, Integer telefonoEmpleado, String emailEmpleado, String claveEmpleado) {
        this.idEmpleado = idEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.emailEmpleado = emailEmpleado;
        this.claveEmpleado = claveEmpleado;
    }
    
    public Empleados(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public Integer getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(Integer telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getClaveEmpleado() {
        return claveEmpleado;
    }

    public void setClaveEmpleado(String claveEmpleado) {
        this.claveEmpleado = claveEmpleado;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
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
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Empleados[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
