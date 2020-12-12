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
@Table(name = "cotizaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotizaciones.findAll", query = "SELECT c FROM Cotizaciones c")
    , @NamedQuery(name = "Cotizaciones.findByIdCotizacion", query = "SELECT c FROM Cotizaciones c WHERE c.idCotizacion = :idCotizacion")
    , @NamedQuery(name = "Cotizaciones.findByFechaCotizacion", query = "SELECT c FROM Cotizaciones c WHERE c.fechaCotizacion = :fechaCotizacion")
    , @NamedQuery(name = "Cotizaciones.findByDescripcionCotizacion", query = "SELECT c FROM Cotizaciones c WHERE c.descripcionCotizacion = :descripcionCotizacion")})
public class Cotizaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;
    @Column(name = "fecha_cotizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCotizacion;
    @Column(name = "descripcion_cotizacion")
    private String descripcionCotizacion;
    @JoinColumn(name = "id_fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Clientes idFkCliente;
    @JoinColumn(name = "id_fk_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleados idFkEmpleado;

    public Cotizaciones() {
    }

    public Cotizaciones(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public String getDescripcionCotizacion() {
        return descripcionCotizacion;
    }

    public void setDescripcionCotizacion(String descripcionCotizacion) {
        this.descripcionCotizacion = descripcionCotizacion;
    }

    public Clientes getIdFkCliente() {
        return idFkCliente;
    }

    public void setIdFkCliente(Clientes idFkCliente) {
        this.idFkCliente = idFkCliente;
    }

    public Empleados getIdFkEmpleado() {
        return idFkEmpleado;
    }

    public void setIdFkEmpleado(Empleados idFkEmpleado) {
        this.idFkEmpleado = idFkEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacion != null ? idCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizaciones)) {
            return false;
        }
        Cotizaciones other = (Cotizaciones) object;
        if ((this.idCotizacion == null && other.idCotizacion != null) || (this.idCotizacion != null && !this.idCotizacion.equals(other.idCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cotizaciones[ idCotizacion=" + idCotizacion + " ]";
    }
    
}
