package Controles;

import Datos.AsistenciaDal;
import Modelos.AsistenciaMd;
import Utilitarios.Utilitarios;

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
import org.zkoss.zul.Checkbox;
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
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class CtrLAsistencia extends GenericForwardComposer {

    private Intbox txtId;
    private Textbox txtFecha;
    private Combobox cbxValor;
    private Combobox cbxCita;
    private Button btnGuardar;
    private Button btnImprimir;
    
    Div formulario;

    private static final long serialVersionUID = 1L;
    

    Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateTime now = LocalDateTime.now();

    Session Session = Sessions.getCurrent();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        txtFecha.setText((dtf.format(now)));
        System.out.println("Fecha asignada");
        util.cargaCombox("SELECT CIT_ID, CONCAT(CIT_DIA, \", \", CIT_HORA, \", \", (SELECT ALU_NOMBRE FROM ALUMNO WHERE ALU_ID = c.Alumno_ALU_ID)) AS CITA FROM CITAS c", cbxCita);
        System.out.println("Combobox Cita");        
        System.out.println("Combobox Valor");
        LlenarGrid();
        
        if(!Session.getAttribute("ROL").toString().equals("A")){
            formulario.setVisible(false);
        }
    }

    public void limpiar() {

        txtId.setText("");
        txtFecha.setText((dtf.format(now)));
        cbxValor.setText("");
        cbxCita.setText("");

        btnGuardar.setDisabled(false);
        //btnImprimir.setDisabled(true);

    }
    
    public void visualizarDatos(){
        System.out.println("ID: " + txtId.getText());
        System.out.println("Fecha: " + txtFecha.getText());
        System.out.println("Asistencia: " + cbxValor.getSelectedItem().getValue().toString());
        System.out.println("Cita: " + cbxCita.getSelectedItem().getValue().toString());
    }

    public void onClick$btnGuardar(Event evt) throws SQLException {

        if (this.txtFecha.getText().equals("") || this.cbxValor.getSelectedItem().getValue().toString().equals("")
                || this.cbxCita.getSelectedItem().getValue().toString().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {
            
                AsistenciaMd modelo = new AsistenciaMd();

                try {
                    visualizarDatos();

                    modelo.setId(txtId.getText());
                    modelo.setFecha(util.cambio_fecha(txtFecha.getText()));
                    modelo.setValor(cbxValor.getSelectedItem().getValue().toString());
                    modelo.setCita(cbxCita.getSelectedItem().getValue().toString());
                    AsistenciaDal bd = new AsistenciaDal();

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

        if (this.txtFecha.getText().equals("") || this.cbxValor.getSelectedItem().getValue().toString().equals("")
                || this.cbxCita.getSelectedItem().getValue().toString().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            AsistenciaMd modelo = new AsistenciaMd();

            try {
                visualizarDatos();

                modelo.setId(txtId.getText());
                modelo.setFecha(txtFecha.getText());
                modelo.setValor(cbxValor.getSelectedItem().getValue().toString());
                modelo.setCita(cbxCita.getSelectedItem().getValue().toString());
                AsistenciaDal bd = new AsistenciaDal();

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
    public List<AsistenciaMd> datos;
    private int banderaGrid = 0;

    public void LlenarGrid() throws SQLException, ParseException {
        AsistenciaDal buscar = new AsistenciaDal();

        datos = buscar.buscaGrid();
        if (banderaGrid == 1) {
            row.getChildren().clear();
            rows.getChildren().clear();
            banderaGrid = 0;
        }

        for (AsistenciaMd mov : datos) {
            banderaGrid = 1;
            Label id = new Label();
            ValoresLabel(id, mov.getId(), "");
            Label fecha = new Label();
            ValoresLabel(fecha, mov.getFecha(), "");
            Label valor = new Label();
            ValoresLabel(valor, mov.getValor(), "");
            Label cita = new Label();
            ValoresLabel(cita, mov.getCita(), "");
            Label alumno = new Label();
            ValoresLabel(alumno, mov.getAlumno(), "");

            Div acciones = new Div();
            acciones.setClass("text-center");

            
            Button Modificar = new Button();
            CreateButton(Modificar, "btn btn-primary btn-md", "z-icon-edit", "margin-left:3px;", "", true);
            acciones.appendChild(Modificar);

            row = new Row();
            row.setStyle("border-style:solid;border-width:1px");
            row.appendChild(id);
            row.appendChild(fecha);
            row.appendChild(valor);
            row.appendChild(cita);
            row.appendChild(alumno);

            row.appendChild(acciones);

            row.setParent(rows);
            row.setValue(mov);

            Modificar.addEventListener("onClick", actualizar);

        }
    }


    EventListener actualizar = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, DocumentException, ParseException {
            AsistenciaMd modelo = new AsistenciaMd();
            AsistenciaDal buscar = new AsistenciaDal();
            String idBuscar = null;
            Button button = (Button) event.getTarget();
            Div div = (Div) button.getParent();
            Row row = (Row) div.getParent();

            modelo = row.getValue();
            idBuscar = modelo.getId();
            modelo = buscar.BuscarClientes(idBuscar);
            txtId.setText(modelo.getId());
            txtFecha.setText(modelo.getFecha());
            cbxValor.setSelectedIndex(Integer.valueOf(modelo.getValor()));
            cbxCita.setSelectedIndex(Integer.valueOf(modelo.getCita())-1);

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
