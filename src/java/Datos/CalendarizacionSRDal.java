package Datos;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Modelos.CalendarizacionMd; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CalendarizacionSRDal {

   
    public List<CalendarizacionMd> buscarGridA(String id) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<CalendarizacionMd> lista = new ArrayList<CalendarizacionMd>();

        CalendarizacionMd Buscar = null;

        String sql = "";

        sql = "select CIT_ID, CIT_DIA, CIT_HORA, concat(Alumno_ALU_ID, ', ', (SELECT(ALU_NOMBRE) From ALUMNO Where ALU_ID = c.Alumno_ALU_ID )), concat(CIT_INSTR, ', ', (SELECT(USR_NOMBRE) From CTRL_USUARIOS Where USR_CODIGO = c.CIT_INSTR )), concat(Categoria_CAT_ID, ', ', (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = c.Categoria_CAT_ID )) FROM CITAS c where Alumno_ALU_ID = ? ORDER BY CIT_ID ASC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            smt.setString(1, id);
            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CalendarizacionMd();

                Buscar.setId(result.getString(1));
                Buscar.setDia(result.getString(2));
                Buscar.setHora(result.getString(3));
                Buscar.setAlu(result.getString(4));
                Buscar.setInst(result.getString(5));
                Buscar.setCat(result.getString(6));
                
                lista.add(Buscar);
            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.desconectar();
                conn.close();
                conn = null;
            }
        }
        return lista;
    }
    
    public List<CalendarizacionMd> buscaGridI(String id) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<CalendarizacionMd> lista = new ArrayList<CalendarizacionMd>();

        CalendarizacionMd Buscar = null;

        String sql = "";

        sql = "select CIT_ID, CIT_DIA, CIT_HORA, concat(Alumno_ALU_ID, ', ', (SELECT(ALU_NOMBRE) From ALUMNO Where ALU_ID = c.Alumno_ALU_ID )), concat(CIT_INSTR, ', ', (SELECT(USR_NOMBRE) From CTRL_USUARIOS Where USR_CODIGO = c.CIT_INSTR )), concat(Categoria_CAT_ID, ', ', (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = c.Categoria_CAT_ID )) FROM CITAS c where CIT_INSTR = ? ORDER BY CIT_ID ASC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);
            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CalendarizacionMd();

                Buscar.setId(result.getString(1));
                Buscar.setDia(result.getString(2));
                Buscar.setHora(result.getString(3));
                Buscar.setAlu(result.getString(4));
                Buscar.setInst(result.getString(5));
                Buscar.setCat(result.getString(6));
                
                lista.add(Buscar);
            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.desconectar();
                conn.close();
                conn = null;
            }
        }
        return lista;
    }
    
    
    
    
    
    
    
    public List<CalendarizacionMd> buscaGrid() throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<CalendarizacionMd> lista = new ArrayList<CalendarizacionMd>();

        CalendarizacionMd Buscar = null;

        String sql = "";

        sql = "select CIT_ID, CIT_DIA, CIT_HORA, concat(Alumno_ALU_ID, ', ', (SELECT(ALU_NOMBRE) From ALUMNO Where ALU_ID = c.Alumno_ALU_ID )), concat(CIT_INSTR, ', ', (SELECT(USR_NOMBRE) From CTRL_USUARIOS Where USR_CODIGO = c.CIT_INSTR )), concat(Categoria_CAT_ID, ', ', (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = c.Categoria_CAT_ID )) FROM CITAS c where CIT_ID = 0 ORDER BY CIT_ID ASC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CalendarizacionMd();

                Buscar.setId(result.getString(1));
                Buscar.setDia(result.getString(2));
                Buscar.setHora(result.getString(3));
                Buscar.setAlu(result.getString(4));
                Buscar.setInst(result.getString(5));
                Buscar.setCat(result.getString(6));
                
                lista.add(Buscar);
            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.desconectar();
                conn.close();
                conn = null;
            }
        }
        return lista;
    }

    
    

    public CalendarizacionMd BuscarClientes(String nit) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        CalendarizacionMd Buscar = null;

        String sql = "";

        sql = "select * FROM CITAS where CIT_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, nit);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CalendarizacionMd();

                Buscar.setId(result.getString(1));
                Buscar.setDia(result.getString(2));
                Buscar.setHora(result.getString(3));
                Buscar.setAlu(result.getString(4));
                Buscar.setInst(result.getString(5));
                Buscar.setCat(result.getString(6));

            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.desconectar();
                conn.close();
                conn = null;
            }
        }
        return Buscar;
    }

}
