package Datos;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Modelos.AsistenciaMd; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDal {

    public int Crear(AsistenciaMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             
            int result = 0;

            try {
                System.out.println("Insert Asistencia");
                System.out.println("ID: " + modelo.getId());
                System.out.println("Fecha: " + modelo.getFecha());
                System.out.println("Asistencia: " + modelo.getValor());
                System.out.println("Cita: " + modelo.getCita());
                String sql = "insert into ASISTENCIA values ((SELECT IFNULL(MAX(ASI_ID), 0)+1 FROM ASISTENCIA a), ?,?,?, (SELECT Alumno_ALU_ID FROM CITAS WHERE CIT_ID = ?))";
                
                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                //smt.setString(1, modelo.getId());
                smt.setString(1, modelo.getFecha());
                smt.setString(2, modelo.getValor());
                smt.setString(3, modelo.getCita());
                smt.setString(4, modelo.getCita());
                
                System.out.println("Datos listos");
                System.out.println("insert into ASISTENCIA values ((SELECT IFNULL(MAX(ASI_ID), 0)+1 FROM ASISTENCIA a), " + modelo.getFecha() + "," + modelo.getValor() + "," + modelo.getCita() + ", (SELECT Alumno_ALU_ID FROM CITAS WHERE CIT_ID = " + modelo.getCita() + "))");

                smt.executeUpdate();
                
                conn.commit();

                return 1;

            } catch (Exception e) {
                conn.rollback();
                return 0;
            } finally {
                if (smt != null) {
                    smt.close();
                }
                if (conn != null) {
                    conex.desconectar();
                    conn.close();
                    conn = null;
                }
            }

        }
    }

    public int Actualizar(AsistenciaMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             int result = 0;

            try {
                System.out.println("Insert Asistencia");
                System.out.println("ID: " + modelo.getId());
                System.out.println("Fecha: " + modelo.getFecha());
                System.out.println("Asistencia: " + modelo.getValor());
                System.out.println("Cita: " + modelo.getCita());
                String sql = "update ASISTENCIA set ASI_VALOR = ? where ASI_ID = ?";
                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);

                smt.setString(1, modelo.getValor().toUpperCase());
                smt.setString(2, modelo.getId());
                smt.executeUpdate();
                conn.commit();

                return 1;

            } catch (Exception e) {
                conn.rollback();
                return 0;
            } finally {
                if (smt != null) {
                    smt.close();
                }
                if (conn != null) {
                    conex.desconectar();
                    conn.close();
                    conn = null;
                }
            }

        }
    }

    public List<AsistenciaMd> buscaGrid() throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<AsistenciaMd> lista = new ArrayList<AsistenciaMd>();

        AsistenciaMd Buscar = null;

        String sql = "";

        sql = "select a.ASI_ID, a.ASI_FECHA, a.ASI_VALOR, CONCAT(c.CIT_DIA, \", \", c.CIT_HORA), ALU_NOMBRE FROM ASISTENCIA a, CITAS c, ALUMNO WHERE a.CITAS_CIT_ID = c.CIT_ID AND a.CITAS_Alumno_ALU_ID = ALU_ID ORDER BY ASI_ID DESC";
        //+ filtro;

        try {
            
smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new AsistenciaMd();

                Buscar.setId(result.getString(1));
                Buscar.setFecha(result.getString(2));
                Buscar.setValor(result.getString(3));
                Buscar.setCita(result.getString(4));
                Buscar.setAlumno(result.getString(5));
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

    public AsistenciaMd BuscarClientes(String id) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        AsistenciaMd Buscar = null;

        String sql = "";

        sql = "select * FROM ASISTENCIA where ASI_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new AsistenciaMd();

                Buscar.setId(result.getString(1));
                Buscar.setFecha(result.getString(2));
                Buscar.setValor(result.getString(3));
                Buscar.setCita(result.getString(4));

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