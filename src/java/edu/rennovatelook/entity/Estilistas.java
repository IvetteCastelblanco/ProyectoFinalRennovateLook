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
@Table(name = "estilistas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estilistas.findAll", query = "SELECT e FROM Estilistas e")
    , @NamedQuery(name = "Estilistas.findByIdEstilista", query = "SELECT e FROM Estilistas e WHERE e.idEstilista = :idEstilista")
    , @NamedQuery(name = "Estilistas.findByNombresEstilista", query = "SELECT e FROM Estilistas e WHERE e.nombresEstilista = :nombresEstilista")
    , @NamedQuery(name = "Estilistas.findByApellidosEstilista", query = "SELECT e FROM Estilistas e WHERE e.apellidosEstilista = :apellidosEstilista")
    , @NamedQuery(name = "Estilistas.findByTelefonoEstilista", query = "SELECT e FROM Estilistas e WHERE e.telefonoEstilista = :telefonoEstilista")
    , @NamedQuery(name = "Estilistas.findByDireccionEstilista", query = "SELECT e FROM Estilistas e WHERE e.direccionEstilista = :direccionEstilista")
    , @NamedQuery(name = "Estilistas.findByEmailEstilista", query = "SELECT e FROM Estilistas e WHERE e.emailEstilista = :emailEstilista")
    , @NamedQuery(name = "Estilistas.findByClaveEstilista", query = "SELECT e FROM Estilistas e WHERE e.claveEstilista = :claveEstilista")})
public class Estilistas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_estilista")
    private Integer idEstilista;
    @Column(name = "nombres_estilista")
    private String nombresEstilista;
    @Column(name = "apellidos_estilista")
    private String apellidosEstilista;
    @Column(name = "telefono_estilista")
    private Integer telefonoEstilista;
    @Column(name = "direccion_estilista")
    private String direccionEstilista;
    @Column(name = "email_estilista")
    private String emailEstilista;
    @Column(name = "clave_estilista")
    private String claveEstilista;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEstilista", fetch = FetchType.LAZY)
    private Collection<Horarios> horariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFkEstilista", fetch = FetchType.LAZY)
    private Collection<Citas> citasCollection;

    public Estilistas() {
    }

    public Estilistas(Integer idEstilista) {
        this.idEstilista = idEstilista;
    }

    public Integer getIdEstilista() {
        return idEstilista;
    }

    public void setIdEstilista(Integer idEstilista) {
        this.idEstilista = idEstilista;
    }

    public String getNombresEstilista() {
        return nombresEstilista;
    }

    public void setNombresEstilista(String nombresEstilista) {
        this.nombresEstilista = nombresEstilista;
    }

    public String getApellidosEstilista() {
        return apellidosEstilista;
    }

    public void setApellidosEstilista(String apellidosEstilista) {
        this.apellidosEstilista = apellidosEstilista;
    }

    public Integer getTelefonoEstilista() {
        return telefonoEstilista;
    }

    public void setTelefonoEstilista(Integer telefonoEstilista) {
        this.telefonoEstilista = telefonoEstilista;
    }

    public String getDireccionEstilista() {
        return direccionEstilista;
    }

    public void setDireccionEstilista(String direccionEstilista) {
        this.direccionEstilista = direccionEstilista;
    }

    public String getEmailEstilista() {
        return emailEstilista;
    }

    public void setEmailEstilista(String emailEstilista) {
        this.emailEstilista = emailEstilista;
    }

    public String getClaveEstilista() {
        return claveEstilista;
    }

    public void setClaveEstilista(String claveEstilista) {
        this.claveEstilista = claveEstilista;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstilista != null ? idEstilista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estilistas)) {
            return false;
        }
        Estilistas other = (Estilistas) object;
        if ((this.idEstilista == null && other.idEstilista != null) || (this.idEstilista != null && !this.idEstilista.equals(other.idEstilista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estilistas[ idEstilista=" + idEstilista + " ]";
    }
    
}
