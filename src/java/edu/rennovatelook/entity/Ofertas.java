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
@Table(name = "ofertas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ofertas.findAll", query = "SELECT o FROM Ofertas o")
    , @NamedQuery(name = "Ofertas.findByIdOferta", query = "SELECT o FROM Ofertas o WHERE o.idOferta = :idOferta")
    , @NamedQuery(name = "Ofertas.findByNombreOferta", query = "SELECT o FROM Ofertas o WHERE o.nombreOferta = :nombreOferta")
    , @NamedQuery(name = "Ofertas.findByFechaOferta", query = "SELECT o FROM Ofertas o WHERE o.fechaOferta = :fechaOferta")
    , @NamedQuery(name = "Ofertas.findByDescripcionOferta", query = "SELECT o FROM Ofertas o WHERE o.descripcionOferta = :descripcionOferta")})
public class Ofertas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_oferta")
    private Integer idOferta;
    @Column(name = "nombre_oferta")
    private String nombreOferta;
    @Column(name = "fecha_oferta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOferta;
    @Column(name = "descripcion_oferta")
    private String descripcionOferta;
    @JoinColumn(name = "id_fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Clientes idFkCliente;

    public Ofertas() {
    }

    public Ofertas(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombreOferta() {
        return nombreOferta;
    }

    public void setNombreOferta(String nombreOferta) {
        this.nombreOferta = nombreOferta;
    }

    public Date getFechaOferta() {
        return fechaOferta;
    }

    public void setFechaOferta(Date fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    public String getDescripcionOferta() {
        return descripcionOferta;
    }

    public void setDescripcionOferta(String descripcionOferta) {
        this.descripcionOferta = descripcionOferta;
    }

    public Clientes getIdFkCliente() {
        return idFkCliente;
    }

    public void setIdFkCliente(Clientes idFkCliente) {
        this.idFkCliente = idFkCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ofertas)) {
            return false;
        }
        Ofertas other = (Ofertas) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ofertas[ idOferta=" + idOferta + " ]";
    }
    
}
