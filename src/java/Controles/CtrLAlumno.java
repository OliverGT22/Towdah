package Controles;

import Datos.AlumnoDal;
import Modelos.AlumnoMd;

//import Util.Reportes;
//import Util.Utilitarios;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.InetAddress;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Timebox;

public class CtrLAlumno extends GenericForwardComposer {

    private Intbox txtId;
    private Textbox txtNombre;
    private Textbox txtDireccion;
    private Intbox txtTelefono;
    private Button btnGuardar;
    private Button btnImprimir;
    
    Div formulario;

    private static final long serialVersionUID = 1L;

//    Utilitarios util = new Utilitarios();

    Session Session = Sessions.getCurrent();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        LlenarGrid();
        if(!Session.getAttribute("ROL").toString().equals("A")){
            formulario.setVisible(false);
        }
    }

    public void limpiar() {

        txtId.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");

        btnGuardar.setDisabled(false);
        //btnImprimir.setDisabled(true);

    }

    public void onClick$btnGuardar(Event evt) {

        if (this.txtNombre.getText().equals("") || this.txtDireccion.getText().equals("")
                || this.txtTelefono.getText().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            AlumnoMd modelo = new AlumnoMd();

            try {

                modelo.setId(txtId.getText());
                modelo.setNombreComercial(txtNombre.getText());
                modelo.setDireccion(txtDireccion.getText());
                modelo.setTelefono(txtTelefono.getText());
                AlumnoDal bd = new AlumnoDal();

                int res = bd.Crear(modelo);
                if (res > 0) { 

                    LlenarGrid();
                    limpiar();
                    Messagebox.show("REGISTRO INGRESADO CON EXITO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("ERROR AL INSERTAR EL REGISTRO, COMUNIQUESE CON SU SUPERVISOR", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }

            } catch (Exception e) {
                Messagebox.show("ERROR AL INSERTAR EL REGISTRO, " + e.toString() + " COMUNIQUESE CON SU SUPERVISOR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }

        }

    }
    
    public void onClick$btnActualizar(Event evt) {

        if (this.txtNombre.getText().equals("") || this.txtDireccion.getText().equals("")
                || this.txtTelefono.getText().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            AlumnoMd modelo = new AlumnoMd();

            try {

                modelo.setId(txtId.getText());
                modelo.setNombreComercial(txtNombre.getText());
                modelo.setDireccion(txtDireccion.getText());
                modelo.setTelefono(txtTelefono.getText());
                AlumnoDal bd = new AlumnoDal();

                int res = bd.Actualizar(modelo);
                if (res > 0) { 

                    LlenarGrid();
                    limpiar();
                    Messagebox.show("REGISTRO ACTUALIZADO CON EXITO", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                } else {
                    Messagebox.show("ERROR AL ACTUALIZAR EL REGISTRO, COMUNIQUESE CON SU SUPERVISOR", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }

            } catch (Exception e) {
                Messagebox.show("ERROR AL INSERTAR EL REGISTRO, " + e.toString() + " COMUNIQUESE CON SU SUPERVISOR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }

        }

    }

    public void onClick$btnLimpiar(Event evt) {

        limpiar();

    }

    private Rows rows;
    private Row row;
    public List<AlumnoMd> datos;
    private int banderaGrid = 0;

    public void LlenarGrid() throws SQLException, ParseException {
        AlumnoDal buscar = new AlumnoDal();

        datos = buscar.buscaGrid();
        if (banderaGrid == 1) {
            row.getChildren().clear();
            rows.getChildren().clear();
            banderaGrid = 0;
        }

        for (AlumnoMd mov : datos) {
            banderaGrid = 1;
            Label id = new Label();
            ValoresLabel(id, mov.getId(), "");
            Label nombre = new Label();
            ValoresLabel(nombre, mov.getNombreComercial(), "");
            Label direccion = new Label();
            ValoresLabel(direccion, mov.getDireccion(), "");
            Label telefono = new Label();
            ValoresLabel(telefono, mov.getTelefono(), "");

            Div acciones = new Div();
            acciones.setClass("text-center");

            
            Button Modificar = new Button();
            CreateButton(Modificar, "btn btn-primary btn-md", "z-icon-edit", "margin-left:3px;", "", true);
            acciones.appendChild(Modificar);

            row = new Row();
            row.setStyle("border-style:solid;border-width:1px");
            row.appendChild(id);
            row.appendChild(nombre);
            row.appendChild(direccion);
            row.appendChild(telefono);

            row.appendChild(acciones);

            row.setParent(rows);
            row.setValue(mov);

            Modificar.addEventListener("onClick", actualizar);

        }
    }


    EventListener actualizar = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, DocumentException, ParseException {
            AlumnoMd modelo = new AlumnoMd();

            Button button = (Button) event.getTarget();
            Div div = (Div) button.getParent();
            Row row = (Row) div.getParent();

            modelo = row.getValue();
            txtId.setText(modelo.getId());
            txtNombre.setText(modelo.getNombreComercial());
            txtDireccion.setText(modelo.getDireccion());
            txtTelefono.setText(modelo.getTelefono());

        }
    };

    public void ValoresLabel(Label label, String valor, String clase) {
        label.setValue(valor);
        label.setClass(clase);
    }

    public void CreateButton(Button button, String clase, String icon, String style, String label, boolean visible) {
        button.setClass(clase);
        button.setIconSclass(icon);
        button.setStyle(style);
        button.setVisible(visible);
        button.setLabel(label);
    }

//    public void onClick$btnImprimir(Event evt) throws SQLException, DocumentException {
//        Reportes rep = new Reportes();
//        
//        rep.ExtraccionBoletas("0");
//    }
}
