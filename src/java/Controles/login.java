package Controles;

import Utilitarios.Commons;
import Autenticacion.authbd;
import Conexion.conexion;
import Datos.UsuarioDal;
import Modelos.UsuarioMd;
import Utilitarios.Utilitarios;

import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.WrongValueException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class login extends Commons {

    private static final long serialVersionUID = 1L;

    private Textbox txtUser;
    private Textbox txtPass;
    String resp;
    authbd SegepcCtrl = new authbd();
    Session session = Sessions.getCurrent();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//        if (validarSesion()) {
//            execution.sendRedirect("menu.zul");
//        }
    }

    public void onClick$btnLogin(Event evt) throws SQLException {
        if (txtUser.getValue() == null || txtUser.getValue().toString().trim().equals("") || txtPass.getValue() == null || txtPass.getValue().toString().trim().equals("")) {
            Messagebox.show("NO INGRESO USUARIO O CLAVE", "INFORMACION", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        try {

            String resp = SegepcCtrl.autUserStr(txtUser.getValue().toUpperCase(), txtPass.getValue());

            if (resp.toUpperCase().equals("OK")) {

                UsuarioDal dal = new UsuarioDal();

                UsuarioMd us = dal.busca(txtUser.getValue().toUpperCase());

                if (us != null) {
                    if (us.getUsrStatus().equals("C")) {

                        session.setAttribute("USUARIO", us.getUsrUsuario());
                        session.setAttribute("OPCION", "1");

                        execution.sendRedirect("CambioClave.zul");

                    } else {
                        if (us.getUsrStatus().equals("I")) {
                            Messagebox.show("USUARIO INACTIVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
                        } else {

                            session.setAttribute("USUARIO", us.getUsrUsuario());
                            session.setAttribute("ROL", us.getUsrRol());
                            session.setAttribute("NOMBRE", us.getUsrNombre());

                            execution.sendRedirect("menu.zul");
                        }
                    }
                } else {
                    Messagebox.show("EL USUARIO NO ESTA REGISTRADO EN LA BASE DE DATOS", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("Error en Autenticación, Usuario o Clave Inválidos", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (WrongValueException e) {
            Messagebox.show("Ocurrió un error al intentar validar el usuario en Base de Datos, intente nuevamente.", "Información", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void onClick$btnRecuperar(Event evt) throws SQLException {
        if (txtUser.getValue() == null || txtUser.getValue().toString().trim().equals("")) {
            Messagebox.show("NO INGRESO USUARIO", "INFORMACION", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        try {
            UsuarioDal dal = new UsuarioDal();

            UsuarioMd us = dal.busca(txtUser.getValue().toUpperCase());

            if (us != null) {
                if (us.getUsrStatus().equals("I")) {
                    Messagebox.show("USUARIO INACTIVO CONTACTE CON SU ADMINISTRADOR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
                } else {

                    session.setAttribute("USUARIO", us.getUsrUsuario());
                    session.setAttribute("OPCION", "2");
                    session.setAttribute("PALABRA", us.getUsrPalabra());

                    execution.sendRedirect("CambioClave.zul");

                }

            } else {
                Messagebox.show("EL USUARIO NO ESTA REGISTRADO EN LA BASE DE DATOS", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }

        } catch (WrongValueException e) {
            Messagebox.show("Ocurrió un error al intentar validar el usuario en Base de Datos, intente nuevamente.", "Información", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    public void onClick$btnHorario (Event event) throws SQLException{
         execution.sendRedirect("HorariosSR.zul");
    }

    public void onOK$txtPass(Event evt) throws SQLException {
        onClick$btnLogin(evt);
    }

}
