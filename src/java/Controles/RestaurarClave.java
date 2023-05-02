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
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class RestaurarClave extends Commons {

    private Textbox txtUsuario;
    private Textbox txtNombre;

    Session Session = Sessions.getCurrent();
    private static final long serialVersionUID = 1L;

    String usuario = "";

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        usuario = Session.getAttribute("USUARIO").toString();

    }

    UsuarioDal ud = new UsuarioDal();
    UsuarioMd um = new UsuarioMd();

    
    public void onChange$txtUsuario(Event evt) throws SQLException{
        um = ud.busca(txtUsuario.getText().toUpperCase());
        
        if(um != null){
            txtNombre.setText(um.getUsrNombre());
        }else{
            Messagebox.show("EL USUARIO BUSCADO NO EXISTE, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.ERROR);
            txtNombre.setText("");
        }            
    }
        
    
    public void onClick$btnGuardar(Event evt) throws SQLException {
 
         
        um.setUsrUsuario(txtUsuario.getText().toUpperCase());
        um.setUsrUsuarioCrea(Session.getAttribute("USUARIO").toString());

        if (!txtUsuario.getText().equals("")) {

            try {

                if (ud.RestaurarClave(um) > 0) {
                    Messagebox.show("RESTAURACION DE CLAVE EXITOSO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("ERROR AL RESTAURAR CLAVE", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }
            } catch (Exception ex) {
                Messagebox.show("Error Interno al RESTAURAR Clave, consulte con su Supervisor".toUpperCase(), "Informacion", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("NO HA INGRESADO EL USUARIO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        }

    }

    public void onClick$btnCancelar(Event evt) {

            execution.sendRedirect("/menu.zul");
        
    }

}
