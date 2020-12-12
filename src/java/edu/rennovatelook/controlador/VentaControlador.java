package edu.rennovatelook.controlador;

import edu.rennovatelook.dao.ClientesJpaController;
import edu.rennovatelook.dao.EmpleadosJpaController;
import edu.rennovatelook.dao.ProductosJpaController;
import edu.rennovatelook.dao.VentasJpaController;
import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Empleados;
import edu.rennovatelook.entity.Productos;
import edu.rennovatelook.entity.Ventas;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class VentaControlador implements Serializable {
    
   

    VentasJpaController ventaDAO;
    Ventas venta;
    int Codigo;
    int cantidad;
    private String cliente;
    float valorTotal;
    String forma;
    Date fecha = new Date();

    List<Ventas> listVentas;
    ClientesJpaController clienteDAO;
    private List<Clientes> listaCliente;

    public VentaControlador() {
    }
    


    public void crearVenta() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ventaDAO = new VentasJpaController();
            Productos pro = buscarProducto(Codigo);
            Clientes cli = new Clientes();
            cli.setIdCliente(Integer.parseInt(cliente));
            Empleados emp = buscarEmpleado(1002271051);
            venta = new Ventas(ventaDAO.getVentasCount() + 1, fecha, forma, cantidad, valorTotal, cli, emp, pro);
            ventaDAO.create(venta);
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Venta Creada Exitosamente",
                            "EXITO!!!!"));
        } catch (Exception ex) {
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se pudo realizar la venta",
                            "Si el problema sigue consulte con el administrador"));
        }
    }

    @PostConstruct
    public void buscarVentas() {
        ventaDAO = new VentasJpaController();
        listVentas = ventaDAO.findVentasEntities();
        clienteDAO = new  ClientesJpaController();
        listaCliente = clienteDAO.findClientesEntities();
        
    }

    public Clientes buscarCliente(int id) {
        ClientesJpaController c = new ClientesJpaController();
        return c.findClientes(id);
    }

    public Productos buscarProducto(int id) {
        ProductosJpaController c = new ProductosJpaController();
        return c.findProductos(id);
    }

    public Empleados buscarEmpleado(int id) {
        EmpleadosJpaController c = new EmpleadosJpaController();
        return c.findEmpleados(id);
    }

    public void borrarVenta(Ventas venta) {
        try {
            ventaDAO = new VentasJpaController();
            ventaDAO.destroy(venta.getIdVenta());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarVenta(Ventas ventas) {
        venta = ventas;
    }

    public void editarVenta() {
        try {
            ventaDAO = new VentasJpaController();
            ventaDAO.edit(venta);
        } catch (Exception ex) {
            Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void descargaReporte() throws SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/pdf");

        try {
            Map parametro = new HashMap();
           
            Connection conec = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/rennovatelook", "Ivette", "123456");
            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/rennovatelook/reportes/ReporteVentas.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename= Reporte de Ventas.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.rennovatelook.controlador.VentaControlador.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.rennovatelook.controlador.VentaControlador.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.rennovatelook.controlador.VentaControlador.descargaReporte() " + q.getMessage());
        }

    }


    public List<Ventas> getListVentas() {
        return listVentas;
    }

    public void setListVentas(List<Ventas> listVentas) {
        this.listVentas = listVentas;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;

    }

    public List<Clientes> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Clientes> listaCliente) {
        this.listaCliente = listaCliente;
    }

}
