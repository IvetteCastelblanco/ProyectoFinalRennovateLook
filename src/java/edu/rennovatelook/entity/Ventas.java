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
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v")
    , @NamedQuery(name = "Ventas.findByIdVenta", query = "SELECT v FROM Ventas v WHERE v.idVenta = :idVenta")
    , @NamedQuery(name = "Ventas.findByFechaVenta", query = "SELECT v FROM Ventas v WHERE v.fechaVenta = :fechaVenta")
    , @NamedQuery(name = "Ventas.findByFormaDePagoVenta", query = "SELECT v FROM Ventas v WHERE v.formaDePagoVenta = :formaDePagoVenta")
    , @NamedQuery(name = "Ventas.findByCantidadVenta", query = "SELECT v FROM Ventas v WHERE v.cantidadVenta = :cantidadVenta")
    , @NamedQuery(name = "Ventas.findByValorTotalVenta", query = "SELECT v FROM Ventas v WHERE v.valorTotalVenta = :valorTotalVenta")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta")
    private Integer idVenta;
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    @Column(name = "forma_de_pago_venta")
    private String formaDePagoVenta;
    @Column(name = "cantidad_venta")
    private Integer cantidadVenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_total_venta")
    private Float valorTotalVenta;
    @JoinColumn(name = "id_fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Clientes idFkCliente;
    @JoinColumn(name = "id_fk_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleados idFkEmpleado;
    @JoinColumn(name = "id_fk_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos idFkProducto;

    public Ventas() {
    }

    public Ventas(Integer idVenta, Date fechaVenta, String formaDePagoVenta, Integer cantidadVenta, Float valorTotalVenta, Clientes idFkCliente, Empleados idFkEmpleado, Productos idFkProducto) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.formaDePagoVenta = formaDePagoVenta;
        this.cantidadVenta = cantidadVenta;
        this.valorTotalVenta = valorTotalVenta;
        this.idFkCliente = idFkCliente;
        this.idFkEmpleado = idFkEmpleado;
        this.idFkProducto = idFkProducto;
    }

    public Ventas(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getFormaDePagoVenta() {
        return formaDePagoVenta;
    }

    public void setFormaDePagoVenta(String formaDePagoVenta) {
        this.formaDePagoVenta = formaDePagoVenta;
    }

    public Integer getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public Float getValorTotalVenta() {
        return valorTotalVenta;
    }

    public void setValorTotalVenta(Float valorTotalVenta) {
        this.valorTotalVenta = valorTotalVenta;
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

    public Productos getIdFkProducto() {
        return idFkProducto;
    }

    public void setIdFkProducto(Productos idFkProducto) {
        this.idFkProducto = idFkProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ventas[ idVenta=" + idVenta + " ]";
    }
    
}
