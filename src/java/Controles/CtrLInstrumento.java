package Controles;

import Datos.InstrumentoDal;
import Datos.CategoriaDal;
import Modelos.CategoriaMd;
import Modelos.InstrumentoMd;
import Modelos.CategoriaMd;

//import Util.Reportes;
import Utilitarios.Utilitarios;
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

public class CtrLInstrumento extends GenericForwardComposer {

    private Intbox txtId;
    private Textbox txtMod;
    private Textbox txtCar;
    private Intbox txtExis;
    
    private Combobox cbxTipo;
    private Combobox cbxPadre; 
    
    private Button btnGuardar;
    private Button btnImprimir;
    private Button btnActualizar;
    
    Div formulario;

    private static final long serialVersionUID = 1L;

    Utilitarios util = new Utilitarios();

    Session Session = Sessions.getCurrent();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
 
        //util.cargaCombox3("select CAT_ID, concat(CAT_DESCRIPCION, \", \", (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = C.CAT_PADRE ))as Categoria from CATEGORIA C", cbxPadre);
        btnActualizar.setDisabled(true);
        LlenarGrid();
        util.cargaCombox2(datosaux, cbxPadre);

        
        if(!Session.getAttribute("ROL").toString().equals("A")){
            formulario.setVisible(false);
        }
    }

    public void limpiar() {

        txtId.setText("");
        txtMod.setText("");
        cbxPadre.setSelectedIndex(cbxPadre.getItemCount()-1);
        txtCar.setText("");
        cbxTipo.setSelectedIndex(cbxTipo.getItemCount()-1);
        txtExis.setText("");

        btnActualizar.setDisabled(true);
        btnGuardar.setDisabled(false);
        //btnImprimir.setDisabled(true);

    }

    public void onClick$btnGuardar(Event evt) {

        if (this.txtMod.getText().equals("") || this.cbxPadre.getText().equals("") || this.cbxTipo.getText().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            InstrumentoMd modelo = new InstrumentoMd();

            try {

                modelo.setId(txtId.getText());
                modelo.setModelo(txtMod.getText().toUpperCase());
                modelo.setTipo(cbxTipo.getText());  
                modelo.setCaracteristicas(txtCar.getText().toUpperCase());
                modelo.setCategoria(cbxPadre.getSelectedItem().getValue().toString());
                modelo.setExist(txtExis.getText());
                InstrumentoDal bd = new InstrumentoDal();

                int res = bd.Crear(modelo);
                if (res > 0) { 

                    LlenarGrid();
                    limpiar();
                    btnActualizar.setDisabled(true);
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

        if (txtId.getText().equals("") || this.txtMod.getText().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else {

            InstrumentoMd modelo = new InstrumentoMd();

            try {

                modelo.setId(txtId.getText());
                modelo.setModelo(txtMod.getText().toUpperCase());
                modelo.setTipo(cbxTipo.getText());  
                modelo.setCaracteristicas(txtCar.getText().toUpperCase());
                modelo.setCategoria(cbxPadre.getSelectedItem().getValue().toString());
                modelo.setExist(txtExis.getText());
               
                InstrumentoDal bd = new InstrumentoDal();

                int res = bd.Actualizar(modelo);
                if (res > 0) { 

                    LlenarGrid();
                    limpiar();
                    btnGuardar.setDisabled(false);
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
    public List<CategoriaMd> datosaux;
    public List<InstrumentoMd> datos;
    
    private int banderaGrid = 0;

    public void LlenarGrid() throws SQLException, ParseException {
        InstrumentoDal buscar = new InstrumentoDal();
        CategoriaDal padres = new CategoriaDal();
        datosaux = padres.buscaGrid();
        datos = buscar.buscaGrid();
        
        if (banderaGrid == 1) {
            row.getChildren().clear();
            rows.getChildren().clear();
            banderaGrid = 0;
        }

        for (InstrumentoMd mov : datos) {
            banderaGrid = 1;
            Label id = new Label();
            ValoresLabel(id, mov.getId(), "");
            
            Label Modelo = new Label();
            ValoresLabel(Modelo, mov.getModelo(), "");
            
            Label Tipo = new Label();
            ValoresLabel(Tipo, mov.getTipo(), "");
            
            Label Caracteristicas = new Label();
            ValoresLabel(Caracteristicas, mov.getCaracteristicas(), "");
            
            Label Categoria = new Label();
            ValoresLabel(Categoria, mov.getCategoria(), "");
            
            Label Existencias = new Label();
            ValoresLabel(Existencias, mov.getExist(), "");

            Div acciones = new Div();
            acciones.setClass("text-center");

            
            Button Modificar = new Button();
            CreateButton(Modificar, "btn btn-primary btn-md", "z-icon-edit", "margin-left:3px;", "", true);
            acciones.appendChild(Modificar);

            row = new Row();
            row.setStyle("border-style:solid;border-width:1px");
            row.appendChild(id);
            row.appendChild(Modelo);
            row.appendChild(Tipo);
            row.appendChild(Caracteristicas);
            row.appendChild(Categoria);
            row.appendChild(Existencias);
         
            row.appendChild(acciones);

            row.setParent(rows);
            row.setValue(mov);

            Modificar.addEventListener("onClick", actualizar);

        }
    }


    EventListener actualizar = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, DocumentException, ParseException {
            InstrumentoMd modelo = new InstrumentoMd();
            InstrumentoDal dal = new InstrumentoDal();
            Button button = (Button) event.getTarget();
            Div div = (Div) button.getParent();
            Row row = (Row) div.getParent();

            modelo = row.getValue();
            modelo = dal.BuscarClientes(modelo.getId());
            txtId.setText(modelo.getId());
            txtMod.setText(modelo.getModelo());
            txtCar.setText(modelo.getCaracteristicas());
            txtExis.setText(modelo.getExist());
            
            int n = Integer.valueOf(modelo.getCategoria());
            cbxPadre.setSelectedIndex(n-1);
     
            btnGuardar.setDisabled(true);
            btnActualizar.setDisabled(false);
            cbxTipo.setText(modelo.getTipo());
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
