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
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c")
    , @NamedQuery(name = "Citas.findByIdCita", query = "SELECT c FROM Citas c WHERE c.idCita = :idCita")
    , @NamedQuery(name = "Citas.findByFechaCita", query = "SELECT c FROM Citas c WHERE c.fechaCita = :fechaCita")
    , @NamedQuery(name = "Citas.findByDescripcionCita", query = "SELECT c FROM Citas c WHERE c.descripcionCita = :descripcionCita")})
public class Citas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Column(name = "fecha_cita")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCita;
    @Column(name = "descripcion_cita")
    private String descripcionCita;
    @JoinColumn(name = "id_fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Clientes idFkCliente;
    @JoinColumn(name = "id_fk_estilista", referencedColumnName = "id_estilista")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estilistas idFkEstilista;
    @JoinColumn(name = "id_fk_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos idFkProducto;
    @JoinColumn(name = "id_fk_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleados idFkEmpleado;

    public Citas() {
    }

    public Citas(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getDescripcionCita() {
        return descripcionCita;
    }

    public void setDescripcionCita(String descripcionCita) {
        this.descripcionCita = descripcionCita;
    }

    public Clientes getIdFkCliente() {
        return idFkCliente;
    }

    public void setIdFkCliente(Clientes idFkCliente) {
        this.idFkCliente = idFkCliente;
    }

    public Estilistas getIdFkEstilista() {
        return idFkEstilista;
    }

    public void setIdFkEstilista(Estilistas idFkEstilista) {
        this.idFkEstilista = idFkEstilista;
    }

    public Productos getIdFkProducto() {
        return idFkProducto;
    }

    public void setIdFkProducto(Productos idFkProducto) {
        this.idFkProducto = idFkProducto;
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
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Citas[ idCita=" + idCita + " ]";
    }
    
}
