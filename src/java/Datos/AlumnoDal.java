package Datos;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Modelos.AlumnoMd; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDal {

    public int Crear(AlumnoMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             
            int result = 0;

            try {
                String sql = "Insert into ALUMNO  values ((SELECT IFNULL(MAX(ALU_ID), 0)+1 FROM ALUMNO a),?,?,?)";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                //smt.setString(1, modelo.getId());
                smt.setString(1, modelo.getNombreComercial().toUpperCase());
                smt.setString(2, modelo.getTelefono());
                smt.setString(3, modelo.getDireccion());
                

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

    public int Actualizar(AlumnoMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             int result = 0;

            try {
                String sql = "update ALUMNO set ALU_NOMBRE = ?, ALU_TELEFONO = ?, ALU_UBICACION = ? where ALU_ID = ?";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);

                smt.setString(1, modelo.getNombreComercial().toUpperCase());
                smt.setString(2, modelo.getTelefono());
                smt.setString(3, modelo.getDireccion());
                smt.setString(4, modelo.getId());
                

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

    public List<AlumnoMd> buscaGrid() throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<AlumnoMd> lista = new ArrayList<AlumnoMd>();

        AlumnoMd Buscar = null;

        String sql = "";

        sql = "select * FROM ALUMNO ORDER BY ALU_ID DESC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new AlumnoMd();

                Buscar.setId(result.getString(1));
                Buscar.setNombreComercial(result.getString(2));
                Buscar.setTelefono(result.getString(3));
                Buscar.setDireccion(result.getString(4));
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

    public AlumnoMd BuscarClientes(String nit) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        AlumnoMd Buscar = null;

        String sql = "";

        sql = "select * FROM ALUMNO where ALU_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, nit);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new AlumnoMd();

                Buscar.setId(result.getString(1));
                Buscar.setNombreComercial(result.getString(2));
                Buscar.setDireccion(result.getString(4));
                Buscar.setTelefono(result.getString(3));

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
