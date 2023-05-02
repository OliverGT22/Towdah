package Controles;

import Utilitarios.Commons;
import Autenticacion.authbd;
import Conexion.conexion;
import Datos.UsuarioDal;
import Modelos.UsuarioMd;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

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
import java.util.ArrayList;
import java.util.List;
import static javax.ws.rs.client.Entity.xml;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;

public class ModificarUsuario extends Commons {

    private Textbox txtUsuario;
    private Textbox txtNombre;
    private Combobox cbxRol;
    private Combobox cbxEstado;
    private Intbox txtContacto;

    Session Session = Sessions.getCurrent();
    private static final long serialVersionUID = 1L;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    UsuarioDal ud = new UsuarioDal();
    UsuarioMd um = new UsuarioMd();

    public void onChange$txtUsuario(Event evt) throws SQLException {
        um = ud.busca(txtUsuario.getText().toUpperCase());

        if (um != null) {
            txtNombre.setText(um.getUsrNombre());
            cbxRol.setSelectedIndex((um.getUsrRol().equals("A") ? 0 : 1));
            cbxEstado.setSelectedIndex((um.getUsrStatus().equals("A") ? 0 : 1));
            txtContacto.setText(um.getContacto());
        } else {
            Messagebox.show("EL USUARIO BUSCADO NO EXISTE, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
            txtNombre.setText("");
            cbxRol.setText("");
            cbxEstado.setText("");
        }
    }

    public void onClick$btnGuardar(Event evt) throws SQLException {

        if (!txtUsuario.getText().equals("") && !txtNombre.getText().equals("") && !cbxRol.getText().equals("") && !cbxEstado.getText().equals("")) {

            um.setUsrUsuario(txtUsuario.getText().toUpperCase());
            um.setUsrNombre(txtNombre.getText().toUpperCase());
            um.setUsrRol(cbxRol.getSelectedItem().getValue().toString());
            um.setUsrStatus(cbxEstado.getSelectedItem().getValue().toString());
            um.setContacto(txtContacto.getText());

            um.setUsrUsuarioCrea(Session.getAttribute("USUARIO").toString());

            try {

                if (ud.ModificarUsuario(um) > 0) {
                    Messagebox.show("MODIFICACION DE USUARIO EXITOSO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("ERROR AL MODIFICAR USUARIO", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }
            } catch (Exception ex) {
                Messagebox.show("Error Interno al MODIFICAR USUARIO, consulte con su Supervisor".toUpperCase(), "Informacion", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("NO HA INGRESADO TODOS LOS DATOS", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        }

    }

    public void onClick$btnCancelar(Event evt) {

        execution.sendRedirect("/menu.zul");

    }

}
