package Utilitarios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.conexion;
import Modelos.CategoriaMd;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

/**
 *
 * @author kevin
 */
public class Utilitarios {

    public Utilitarios() {
    }

    public int BuscaIndice(Combobox combo, String codigo) {
        int valor = -1;

        for (int i = 0; i < combo.getItemCount(); i++) {
            if (codigo.equals(combo.getItemAtIndex(i).getValue())) {
                valor = i;
                break;
            }
        }

        return valor;
    }
    
    public void cargaCombox(String paquete, Combobox co) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet rst = null;

        co.getItems().clear();

        try {

            Comboitem item = new Comboitem();
            smt = conn.prepareStatement(paquete);
            rst = smt.executeQuery();

            while (rst.next()) {
                item = new Comboitem();
                item.setLabel(rst.getString(2));
                item.setValue(rst.getString(1));
                item.setParent(co);
            }

            co.setValue(null);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (rst != null) {
                smt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public void cargaCombox2(List<CategoriaMd> consulta, Combobox co) {

        co.getItems().clear();

        Comboitem item = new Comboitem();

        for (CategoriaMd buscar:consulta) {
            item = new Comboitem();
            item.setLabel(buscar.getDescripcion());
            item.setValue(buscar.getId());
            item.setParent(co);
        }

        co.setValue(null);

        
    }
    
        public void cargaCombox3(List<String> opciones, Combobox co) {

        co.getItems().clear();
        int numOpcion = 0;
        Comboitem item = new Comboitem();
        for (String buscar:opciones) {
            item = new Comboitem();
            item.setLabel(buscar);
            item.setValue(String.valueOf(numOpcion));
            item.setParent(co);
            numOpcion++;
        }

        co.setValue(null);

        
    }
    
    public String cambio_fecha(String fecha) {
        String f = "";

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            f = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
        }
        return f;
    }

    public String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        return encriptado;
    }

    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);

        return datos;
    }

    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

        return secretKey;
    }

}
