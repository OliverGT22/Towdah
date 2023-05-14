package Controles;

import Datos.CalendarizacionSRDal;
import Modelos.CalendarizacionMd;

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

public class CtrLCalendarizacionSR extends GenericForwardComposer {


    
    private Combobox cbxAlu;
    private Combobox cbxIns;
    
    
    
    private Button btnGuardar;
    private Button btnRest;
    
    Div formulario;

    private static final long serialVersionUID = 1L;

   Utilitarios util = new Utilitarios();


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        util.cargaCombox("select * from ALUMNO", cbxAlu);
        util.cargaCombox("select USR_CODIGO, USR_NOMBRE from CTRL_USUARIOS where USR_ROL = 'O'", cbxIns);

        LlenarGrid(1, "");
        
    }

    public void limpiar() throws SQLException, ParseException {


        cbxAlu.setText("");
        cbxIns.setText("");
        LlenarGrid(1, "");

        btnGuardar.setDisabled(false);
        //btnImprimir.setDisabled(true);

    }

    public void onClick$btnGuardar(Event evt) throws SQLException, ParseException {

        if (this.cbxAlu.getText().equals("") && this.cbxIns.getText().equals("")) {

            Messagebox.show("FALTAN DATOS QUE INGRESAR", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        } else if(this.cbxAlu.getText().equals("") ^ this.cbxIns.getText().equals("")){

            CalendarizacionMd modelo = new CalendarizacionMd();

            try {
               if(this.cbxIns.getText().equals("")){
                   LlenarGrid(2, cbxAlu.getSelectedItem().getValue().toString());
               }else{
                   LlenarGrid(3, cbxIns.getSelectedItem().getValue().toString());
               }

            } catch (Exception e) {
                Messagebox.show("ERROR EN LA BUSQUEDA, " + e.toString() + " COMUNIQUESE CON SU SUPERVISOR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }
            
        }else{
             Messagebox.show("SOLO SELECCIONE UNO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
             limpiar();
        }
        

    }
    

    public void onClick$btnRest(Event evt) throws SQLException, ParseException {

        limpiar();

    }

    private Rows rows;
    private Row row;
    public List<CalendarizacionMd> datos;
    private int banderaGrid = 0;

    public void LlenarGrid(int opcion, String dato) throws SQLException, ParseException {
        CalendarizacionSRDal buscar = new CalendarizacionSRDal();

        switch(opcion){
            case 1:
                datos = buscar.buscaGrid();
                break;
            case 2:
                datos = buscar.buscarGridA(dato);
                break;
            case 3:
                datos = buscar.buscaGridI(dato);
                break;
        }
            
        
        
        if (banderaGrid == 1) {
            row.getChildren().clear();
            rows.getChildren().clear();
            banderaGrid = 0;
        }

        for (CalendarizacionMd mov : datos) {
            banderaGrid = 1;
            Label id = new Label();
            ValoresLabel(id, mov.getId(), "");
            
            Label Dia = new Label();
            ValoresLabel(Dia, mov.getDia(), "");
            
            Label Hora = new Label();
            ValoresLabel(Hora, mov.getHora(), "");
            
            Label Alumno = new Label();
            ValoresLabel(Alumno, mov.getAlu(), "");
            
            Label Instructor = new Label();
            ValoresLabel(Instructor, mov.getInst(), "");
            
            Label Categoria = new Label();
            ValoresLabel(Categoria, mov.getCat(), "");

            

            


            row = new Row();
            row.setStyle("border-style:solid;border-width:1px");
            row.appendChild(id);
            row.appendChild(Dia);
            row.appendChild(Hora);
            row.appendChild(Alumno);
            row.appendChild(Instructor);
            row.appendChild(Categoria);


            row.setParent(rows);
            row.setValue(mov);

        

        }
    }


   

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
