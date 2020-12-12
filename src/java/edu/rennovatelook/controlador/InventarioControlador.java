package edu.rennovatelook.controlador;

import edu.rennovatelook.dao.ProductosJpaController;
import edu.rennovatelook.dao.ProveedoresJpaController;
import edu.rennovatelook.dao.exceptions.IllegalOrphanException;
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
import edu.rennovatelook.entity.Productos;
import edu.rennovatelook.entity.Proveedores;
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
public class InventarioControlador implements Serializable {

    String Proveedores;
    Productos pro = new Productos();

    ProductosJpaController productoDAO;
    List<Productos> listaPro;

    ProveedoresJpaController provDAO;
    List<Proveedores> listaProv;

    public InventarioControlador() {
    }

    @PostConstruct
    public void buscarInven() {
        productoDAO = new ProductosJpaController();
        listaPro = productoDAO.findProductosEntities();

        provDAO = new ProveedoresJpaController();
        listaProv = provDAO.findProveedoresEntities();
    }

    public void crearProducto() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Proveedores p = new Proveedores();
            productoDAO = new ProductosJpaController();
            for (int i = 0; i < listaProv.size(); i++) {
                if (Integer.parseInt(Proveedores) == listaProv.get(i).getIdProveedor()) {
                    p = listaProv.get(i);
                    i = listaProv.size();
                }
            }
            pro.setIdFkProveedor(p);
            productoDAO.create(pro);
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Producto creado exitosamente",
                            "EXITO!!!!"));
        } catch (Exception ex) {
            context.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se pudo crear el producto",
                            "Si el problema sigue consulte con el administrador"));
        }
    }
    
    public void borrarProducto(Productos p) {
        try {
            productoDAO = new ProductosJpaController();
            productoDAO.destroy(p.getIdProducto());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(InventarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void guardarProducto(Productos p) {
        pro = p;
    }

    public void editarProducto() {
        try {
            productoDAO = new ProductosJpaController();
            productoDAO.edit(pro);
        } catch (Exception ex) {
            Logger.getLogger(InventarioControlador.class.getName()).log(Level.SEVERE, null, ex);
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
            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/rennovatelook/reportes/ReporteInventario.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename= Reporte de Inventario.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.rennovatelook.controlador.InventarioControlador.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.rennovatelook.controlador.InventarioControlador.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.rennovatelook.controlador.InventarioControlador.descargaReporte() " + q.getMessage());
        }

    }

    public String getProveedores() {
        return Proveedores;
    }

    public void setProveedores(String Proveedores) {
        this.Proveedores = Proveedores;
    }

    public Productos getPro() {
        return pro;
    }

    public void setPro(Productos pro) {
        this.pro = pro;
    }

    public List<Productos> getListaPro() {
        return listaPro;
    }

    public void setListaPro(List<Productos> listaPro) {
        this.listaPro = listaPro;
    }

    public List<Proveedores> getListaProv() {
        return listaProv;
    }

    public void setListaProv(List<Proveedores> listaProv) {
        this.listaProv = listaProv;
    }

}
