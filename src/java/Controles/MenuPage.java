package Controles;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class MenuPage extends GenericForwardComposer {

    private Include pagina;
    private Label lbInicio;
    private Label lbUser;
    private Menu mnuAdmin;

    Session Session = Sessions.getCurrent();

    String permiso = "";
    String user = "";
    String nombre = "";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (validarSesion()) {

            user = Session.getAttribute("USUARIO").toString();
            permiso = Session.getAttribute("ROL").toString();
            nombre = Session.getAttribute("NOMBRE").toString();
            Permisos(permiso);

            lbUser.setValue(nombre);
            pagina.setSrc("/inicio.zul");

            lbInicio.setValue("BIENVENIDO AL SISTEMA");

        } else {
            desktop.getSession().invalidate();
            Session.invalidate();
            execution.sendRedirect("/login.zul");
        }

    }

    public void Permisos(String permiso) {
        if (permiso.equals("O")) {
            mnuAdmin.setVisible(false);
        }
        if (permiso.equals("M")) {
            mnuAdmin.setVisible(false);
        }
    }

    public boolean validarSesion() {
        if (Session.getAttribute("USUARIO") != null) {
            return true;
        }
        return false;
    }

    public void onClick$rgstrCat(Event even){
        pagina.setSrc("/Views/Registros/Categoria.zul");
        lbInicio.setValue("Registro Categoria");
    }
    
    public void onClick$rgstrIst(Event even){
        pagina.setSrc("/Views/Registros/Instrumento.zul");
        lbInicio.setValue("Registro Instrumento");
    }
    
    public void onClick$rgstrAlu(Event even) {
        pagina.setSrc("/Views/Registros/Alumnos.zul");
        lbInicio.setValue("Registro Alumnos");
    }
    
    public void onClick$itemCitasd(Event even) {
        pagina.setSrc("/Views/Citas/Calendarizacion.zul");
        lbInicio.setValue("Calendarizacion Citas");
    }
    
    public void onClick$iCreaUsuario(Event even) {
        pagina.setSrc("/CreacionUsuario.zul");
        lbInicio.setValue("CREACION DE USUARIOS");
    }


    
    public void onClick$itemProv(Event even) {
        pagina.setSrc("/Proveedores.zul");
        lbInicio.setValue("CATALOGO DE PROVEEDORES");
    }
    
    public void onClick$itemProd(Event even) {
        pagina.setSrc("/Productos.zul");
        lbInicio.setValue("CATALOGO DE PRODUCTOS");
    }

    
    public void onClick$iFact(Event even) {
        pagina.setSrc("/Views/ventas.zul");
        lbInicio.setValue("FACTURACION");
    }
    
    public void onClick$iComp(Event even) {
        pagina.setSrc("/Views/Productos_Compras.zul");
        lbInicio.setValue("COMPRAS");
    }

    public void onClick$iDev(Event even) {
        pagina.setSrc("/Views/Productos_Devolucion.zul");
        lbInicio.setValue("DEVOLUCIONES");
    }
    
    public void onClick$iCambioClaves(Event even) {
        session.setAttribute("OPCION", "3");
        pagina.setSrc("/CambioClave.zul");
        lbInicio.setValue("");
    }

    public void onClick$iCambioClave(Event even) {
        pagina.setSrc("/RestaurarClave.zul");
        lbInicio.setValue("RESTAURAR CLAVE");
    }

    public void onClick$iModificaUsuario(Event even) {
        pagina.setSrc("/ModificacionUsuario.zul");
        lbInicio.setValue("MODIFICAR USUARIO");
    }


    public void onClick$itemSalir(Event evt) {
        desktop.getSession().invalidate();
        Session.invalidate();
        execution.sendRedirect("/login.zul");
    }
}
