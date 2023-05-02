package Datos;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Modelos.CategoriaMd; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDal {

    public int Crear(CategoriaMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             
            int result = 0;

            try {
                String sql = "insert into CATEGORIA values ((SELECT IFNULL(MAX(CAT_ID), 0)+1 FROM CATEGORIA c),?,?)";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                smt.setString(1, modelo.getDescripcion().toUpperCase());
                smt.setString(2, modelo.getPadre());

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

    public int Actualizar(CategoriaMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             int result = 0;

            try {
                String sql = "update CATEGORIA set CAT_DESCRIPCION = ?, CAT_PADRE = ? where CAT_ID = ?";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);

                smt.setString(1, modelo.getDescripcion().toUpperCase());
                smt.setString(2, modelo.getPadre());
                smt.setString(3, modelo.getId());
                

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
    
    public String buscarPadre(List<CategoriaMd> consulta, String idBuscar)
    {
        String registro = "";
        for (CategoriaMd mov: consulta)
        {
            if (mov.getId().equals(idBuscar))
            {
                registro = mov.getDescripcion() + " " + registro;
                if (!mov.getPadre().equals("0"))
                    registro = buscarPadre(consulta, mov.getPadre()) + registro;
                break;
            }
        }
        return registro;
    }

    public List<CategoriaMd> buscaGrid() throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        List<CategoriaMd> lista = new ArrayList<CategoriaMd>();
        List<CategoriaMd> lista_2 = new ArrayList<CategoriaMd>();

        CategoriaMd Buscar = null;

        String sql = "";

        /*
        sql = "select CAT_ID, CAT_DESCRIPCION, concat(CAT_PADRE, \", \", (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = c.CAT_PADRE )) FROM CATEGORIA c ORDER BY CAT_ID DESC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CategoriaMd();

                Buscar.setId(result.getString(1));
                Buscar.setDescripcion(result.getString(2));
                Buscar.setPadre(result.getString(3));
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
        */
        // segunda forma (auxiliar)
        sql = "select CAT_ID, CAT_DESCRIPCION, CAT_PADRE FROM CATEGORIA ORDER BY CAT_ID ASC";
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {                     // Recorrer la consulta
                Buscar = new CategoriaMd();
                Buscar.setId(result.getString(1));
                Buscar.setDescripcion(result.getString(2));
                Buscar.setPadre(result.getString(3));
                lista_2.add(Buscar);
            }
            for (CategoriaMd aux: lista_2)
            {
                CategoriaMd buscar2;
                buscar2 = new CategoriaMd();
                buscar2.setId(aux.getId());
                buscar2.setDescripcion(aux.getDescripcion());
                buscar2.setPadre(aux.getPadre());
                if (!aux.getPadre().equals("0"))
                {
                    buscar2.setDescripcion(buscarPadre(lista_2, aux.getPadre()) + aux.getDescripcion());
                }
                lista.add(buscar2);
                System.out.println(aux.getPadre());
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

    public CategoriaMd BuscarClientes(String nit) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        CategoriaMd Buscar = null;

        String sql = "";

        sql = "select * FROM CATEGORIA where CAT_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, nit);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new CategoriaMd();

                Buscar.setId(result.getString(1));
                Buscar.setDescripcion(result.getString(2));
                Buscar.setPadre(result.getString(3));

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
