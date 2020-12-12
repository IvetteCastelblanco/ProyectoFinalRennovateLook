package edu.rennovatelook.controlador;

import edu.rennovatelook.dao.CitasJpaController;
import edu.rennovatelook.dao.ClientesJpaController;
import edu.rennovatelook.dao.EmpleadosJpaController;
import edu.rennovatelook.dao.EstilistasJpaController;
import edu.rennovatelook.dao.ProductosJpaController;
import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import edu.rennovatelook.entity.Citas;
import edu.rennovatelook.entity.Clientes;
import edu.rennovatelook.entity.Empleados;
import edu.rennovatelook.entity.Estilistas;
import edu.rennovatelook.entity.Productos;
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
public class AgendaControlador implements Serializable {

    Citas ci = new Citas();
    String cliente;
    String producto;
    String estilis;
    String empl;

    CitasJpaController citaDAO;
    List<Citas> listaCita;

    ClientesJpaController clienteDAO;
    List<Clientes> listaCliente;

    EstilistasJpaController estilisDAO;
    List<Estilistas> listaEstilis;

    ProductosJpaController producDAO;
    List<Productos> listaProduct;

    EmpleadosJpaController empleDAO;
    List<Empleados> listaEmpl;

    public AgendaControlador() {
    }

    @PostConstruct
    public void buscarCitas() {
        citaDAO = new CitasJpaController();
        listaCita = citaDAO.findCitasEntities();

        clienteDAO = new ClientesJpaController();
        listaCliente = clienteDAO.findClientesEntities();

        estilisDAO = new EstilistasJpaController();
        listaEstilis = estilisDAO.findEstilistasEntities();

        producDAO = new ProductosJpaController();
        listaProduct = producDAO.findProductosEntities();

        empleDAO = new EmpleadosJpaController();
        listaEmpl = empleDAO.findEmpleadosEntities();
    }

    public void crearAgenda() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Clientes c = new Clientes();
            Estilistas e = new Estilistas();
            Productos p = new Productos();
            Empleados em = new Empleados();
            citaDAO = new CitasJpaController();
            for (int i = 0; i < listaCliente.size(); i++) {
                if (Integer.parseInt(cliente) == listaCliente.get(i).getIdCliente()) {
                    c = listaCliente.get(i);
                    i = listaCliente.size();
                }
            }
            for (int i = 0; i < listaEstilis.size(); i++) {
                if (Integer.parseInt(estilis) == listaEstilis.get(i).getIdEstilista()) {
                    e = listaEstilis.get(i);
                    i = listaEstilis.size();
                }
            }
            for (int i = 0; i < listaProduct.size(); i++) {
                if (Integer.parseInt(producto) == listaProduct.get(i).getIdProducto()) {
                    p = listaProduct.get(i);
                    i = listaProduct.size();
                }
            }
            for (int i = 0; i < listaEmpl.size(); i++) {
                if (Integer.parseInt(empl) == listaEmpl.get(i).getIdEmpleado()) {
                    em = listaEmpl.get(i);
                    i = listaEmpl.size();
                }
            }
            ci.setIdFkCliente(c);
            ci.setIdFkEmpleado(em);
            ci.setIdFkEstilista(e);
            ci.setIdFkProducto(p);
            citaDAO.create(ci);
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "La Cita fue Creada Exitosamente",
                            "EXITO!!!!"));
        } catch (Exception ex) {
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se pudo crear la Cita",
                            "Si el problema sigue consulte con el administrador"));
        }
    }

    public void borrarCita(Citas c) {
        try {
            citaDAO = new CitasJpaController();
            citaDAO.destroy(c.getIdCita());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AgendaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarCita(Citas c) {
        ci = c;
    }

    public void editarCita() {
        try {
            citaDAO = new CitasJpaController();
            citaDAO.edit(ci);
        } catch (Exception ex) {
            Logger.getLogger(AgendaControlador.class.getName()).log(Level.SEVERE, null, ex);
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
            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/rennovatelook/reportes/ReporteAgenda.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename= Reporte de Citas.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.rennovatelook.controlador.AgendaControlador.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.rennovatelook.controlador.AgendaControlador.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.rennovatelook.controlador.AgendaControlador.descargaReporte() " + q.getMessage());
        }

    }


    public Citas getCi() {
        return ci;
    }

    public void setCi(Citas ci) {
        this.ci = ci;
    }

    public List<Clientes> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Clientes> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public List<Estilistas> getListaEstilis() {
        return listaEstilis;
    }

    public void setListaEstilis(List<Estilistas> listaEstilis) {
        this.listaEstilis = listaEstilis;
    }

    public List<Productos> getListaProduct() {
        return listaProduct;
    }

    public void setListaProduct(List<Productos> listaProduct) {
        this.listaProduct = listaProduct;
    }

    public List<Empleados> getListaEmpl() {
        return listaEmpl;
    }

    public void setListaEmpl(List<Empleados> listaEmpl) {
        this.listaEmpl = listaEmpl;
    }

    public List<Citas> getListaCita() {
        return listaCita;
    }

    public void setListaCita(List<Citas> listaCita) {
        this.listaCita = listaCita;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getEstilis() {
        return estilis;
    }

    public void setEstilis(String estilis) {
        this.estilis = estilis;
    }

    public String getEmpl() {
        return empl;
    }

    public void setEmpl(String empl) {
        this.empl = empl;
    }

}
