package Controles;





import Utilitarios.Commons;
import Autenticacion.authbd;
import Conexion.conexion;
import Datos.UsuarioDal;
import Modelos.UsuarioMd;
import Utilitarios.Utilitarios;
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

public class CambioClave extends Commons {

    private Textbox txtUsuario;
    private Textbox txtClave;
    private Textbox txtClave2;
    private Textbox txtPalabra;
    private Label lbInicio;
    private Label lbPalabra;
    private Div div1;
 
    Session Session = Sessions.getCurrent();
    private static final long serialVersionUID = 1L;

    String usuario = "";
    String operacion = "";

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        usuario = Session.getAttribute("USUARIO").toString();
        operacion = Session.getAttribute("OPCION").toString();

        txtUsuario.setText(usuario);

        if (operacion.equals("1")) {

            lbInicio.setValue("CAMBIO DE CLAVE INICIAL");
            Messagebox.show("PRIMERA AUTENTICACION, DEBE CAMBIAR CLAVE Y PALABRA SECRETA", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            if (operacion.equals("2")) {
                lbInicio.setValue("CAMBIO DE CLAVE");
                Messagebox.show("CAMBIO DE CLAVE, INGRESE SU PALABRA SECRETA", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                lbInicio.setValue("CAMBIO DE CLAVE");
                Messagebox.show("CAMBIO DE CLAVE, INGRESE SU NUEVA CLAVE", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
                div1.setVisible(false);
                this.txtPalabra.setVisible(false);
                this.lbPalabra.setVisible(false);
                
            }
        }

    }

    public void onClick$btnGuardar(Event evt) throws SQLException {

        UsuarioDal ud = new UsuarioDal();

        UsuarioMd um = new UsuarioMd();

        um.setUsrUsuario(txtUsuario.getText().toUpperCase());
        um.setUsrPalabra(txtPalabra.getText());
        um.setUsrClave(txtClave.getText());
        um.setUsrUsuarioCrea(Session.getAttribute("USUARIO").toString());

        if (operacion.equals("1")) {
            if (!txtClave.getText().equals("") && !txtClave2.getText().equals("") && !txtPalabra.getText().equals("")) {
                if (txtClave.getText().equals(txtClave2.getText())) {

                    try {

                        if (ud.CambioClave(um) > 0) {
                            Messagebox.show("CAMBIO DE CLAVE EXITOSO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                            execution.sendRedirect("login.zul");
                        } else {
                            Messagebox.show("ERROR AL CAMBIAR CLAVE", "Informacion", Messagebox.OK, Messagebox.ERROR);
                        }
                    } catch (Exception ex) {
                        Messagebox.show("Error Interno al Cambiar Clave, consulte con su Supervisor", "Informacion", Messagebox.OK, Messagebox.ERROR);
                    }
                } else {
                    Messagebox.show("LAS CLAVES NO COINCIDEN, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

                }

            }
        } else {
            if (operacion.equals("2")) {
                if (!txtClave.getText().equals("") && !txtClave2.getText().equals("") && !txtPalabra.getText().equals("")) {
                    if (txtClave.getText().equals(txtClave2.getText())) {

                        Utilitarios ut = new Utilitarios();
                        
                        try {
                            if (session.getAttribute("PALABRA").toString().equals(ut.encriptar(txtPalabra.getText(),"x55s88d"))) {
                                if (ud.CambioClave(um) > 0) {
                                    Messagebox.show("CAMBIO DE CLAVE EXITOSO", "Informacion", Messagebox.OK, Messagebox.INFORMATION); 
                                    execution.sendRedirect("login.zul");
                                } else {
                                    Messagebox.show("ERROR AL CAMBIAR CLAVE", "Informacion", Messagebox.OK, Messagebox.ERROR);
                                }
                            } else {
                                Messagebox.show("LA PALABRA SECRETA NO ES CORRECTA", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } catch (Exception ex) {
                            Messagebox.show("Error Interno al Cambiar Clave, consulte con su Supervisor".toUpperCase(), "Informacion", Messagebox.OK, Messagebox.ERROR);
                        }
                    } else {
                        Messagebox.show("LAS CLAVES NO COINCIDEN, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

                    }

                }
            } else {
                if (!txtClave.getText().equals("") && !txtClave2.getText().equals("")) {
                    if (txtClave.getText().equals(txtClave2.getText())) {

                        try {
                                if (ud.CambioClave(um) > 0) {
                                    Messagebox.show("CAMBIO DE CLAVE EXITOSO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                                    execution.sendRedirect("login.zul");
                                } else {
                                    Messagebox.show("ERROR AL CAMBIAR CLAVE", "Informacion", Messagebox.OK, Messagebox.ERROR);
                                }
                           
                        } catch (Exception ex) {
                            Messagebox.show("Error Interno al Cambiar Clave, consulte con su Supervisor".toUpperCase(), "Informacion", Messagebox.OK, Messagebox.ERROR);
                        }
                    } else {
                        Messagebox.show("LAS CLAVES NO COINCIDEN, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

                    }

                }

            }
        }
    }

    public void onClick$btnCancelar(Event evt) {

        if(operacion.equals("3")){
            execution.sendRedirect("/menu.zul");
        }else{
            execution.sendRedirect("/index.zul");
        }

    }

}
