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
@Table(name = "horarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h")
    , @NamedQuery(name = "Horarios.findByIdHorario", query = "SELECT h FROM Horarios h WHERE h.idHorario = :idHorario")
    , @NamedQuery(name = "Horarios.findByFechaInicioHorario", query = "SELECT h FROM Horarios h WHERE h.fechaInicioHorario = :fechaInicioHorario")
    , @NamedQuery(name = "Horarios.findByFechaFinHorario", query = "SELECT h FROM Horarios h WHERE h.fechaFinHorario = :fechaFinHorario")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_horario")
    private Integer idHorario;
    @Column(name = "fecha_inicio_horario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioHorario;
    @Column(name = "fecha_fin_horario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinHorario;
    @JoinColumn(name = "id_fk_estilista", referencedColumnName = "id_estilista")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estilistas idFkEstilista;
    @JoinColumn(name = "id_fk_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleados idFkEmpleado;

    public Horarios() {
    }

    public Horarios(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Date getFechaInicioHorario() {
        return fechaInicioHorario;
    }

    public void setFechaInicioHorario(Date fechaInicioHorario) {
        this.fechaInicioHorario = fechaInicioHorario;
    }

    public Date getFechaFinHorario() {
        return fechaFinHorario;
    }

    public void setFechaFinHorario(Date fechaFinHorario) {
        this.fechaFinHorario = fechaFinHorario;
    }

    public Estilistas getIdFkEstilista() {
        return idFkEstilista;
    }

    public void setIdFkEstilista(Estilistas idFkEstilista) {
        this.idFkEstilista = idFkEstilista;
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
        hash += (idHorario != null ? idHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.idHorario == null && other.idHorario != null) || (this.idHorario != null && !this.idHorario.equals(other.idHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Horarios[ idHorario=" + idHorario + " ]";
    }
    
}
