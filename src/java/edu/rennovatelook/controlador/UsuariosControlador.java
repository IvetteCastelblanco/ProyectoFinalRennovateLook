package edu.rennovatelook.controlador;

import edu.rennovatelook.dao.EmpleadosJpaController;
import edu.rennovatelook.dao.exceptions.IllegalOrphanException;
import edu.rennovatelook.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import edu.rennovatelook.entity.Empleados;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@RequestScoped
public class UsuariosControlador implements Serializable {

  

    Empleados emp = new Empleados();
    EmpleadosJpaController empleadoDAO;
    List<Empleados> listaEmp;

    public UsuariosControlador() {
    }

    @PostConstruct
    public void buscarInven() {
        empleadoDAO = new EmpleadosJpaController();
        listaEmp = empleadoDAO.findEmpleadosEntities();
    }

    public void crearProducto() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            empleadoDAO = new EmpleadosJpaController();
            empleadoDAO.create(emp);
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "El Usuario fue Creado Exitosamente",
                            "EXITO!!!!"));
        } catch (Exception ex) {
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se pudo crear el usuario",
                            "Si el problema sigue consulte con el administrador"));
        }
    }

    public void borrarUsuario(Empleados emp) {
        try {
            empleadoDAO = new EmpleadosJpaController();
            empleadoDAO.destroy(emp.getIdEmpleado());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(UsuariosControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarUsuario(Empleados empl) {
        emp = empl;
    }

    public void editarUsuario() {
        try {
            empleadoDAO = new EmpleadosJpaController();
            empleadoDAO.edit(emp);
        } catch (Exception ex) {
            Logger.getLogger(UsuariosControlador.class.getName()).log(Level.SEVERE, null, ex);
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
            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/rennovatelook/reportes/ReportesUsuarios.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename= Lista Usuarios.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.rennovatelook.controlador.UsuariosControlador.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.rennovatelook.controlador.UsuariosControlador.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.rennovatelook.controlador.UsuariosControlador.descargaReporte() " + q.getMessage());
        }

    }

    public Empleados getEmp() {
        return emp;
    }

    public void setEmp(Empleados emp) {
        this.emp = emp;
    }

    public List<Empleados> getListaEmp() {
        return listaEmp;
    }

    public void setListaEmp(List<Empleados> listaEmp) {
        this.listaEmp = listaEmp;
    }

   
}
