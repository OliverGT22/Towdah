package Datos;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Modelos.InstrumentoMd;
import Modelos.CategoriaMd;
import Datos.CategoriaDal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstrumentoDal {

    public int Crear(InstrumentoMd modelo) throws SQLException {
        {
            PreparedStatement smt = null, smt2 = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             
            int result = 0;

            try {
                String sql = "insert into INSTRUMENTO values ((SELECT IFNULL(MAX(INST_ID), 0)+1 FROM INSTRUMENTO i),?,?,?,?)";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                smt.setString(1, modelo.getModelo().toUpperCase());
                smt.setString(2, modelo.getTipo());
                smt.setString(3, modelo.getCaracteristicas().toUpperCase());
                smt.setString(4, modelo.getCategoria());

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

    public int Actualizar(InstrumentoMd modelo) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
             int result = 0;

            try {
                String sql = "update INSTRUMENTO set INST_MODELO = ?, INST_TIPO = ?, INST_CARACT = ?, Categoria_CAT_ID = ? where INST_ID = ?";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);

                smt.setString(1, modelo.getModelo().toUpperCase());
                smt.setString(2, modelo.getTipo());
                smt.setString(3, modelo.getCaracteristicas());
                smt.setString(4, modelo.getCategoria());
                smt.setString(5, modelo.getId());
                

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
    
    public String buscarPadre(List<InstrumentoMd> consulta, String idBuscar)
    {
        String registro = "";
        for (InstrumentoMd mov: consulta)
        {
            if (mov.getId().equals(idBuscar))
            {
                registro = mov.getModelo() + " " + registro;
                if (!mov.getTipo().equals("0"))
                    registro = buscarPadre(consulta, mov.getTipo()) + registro;
                break;
            }
        }
        return registro;
    }

    public List<InstrumentoMd> buscaGrid() throws SQLException {

        PreparedStatement smt = null, smt2 = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null, r2 = null;
        List<InstrumentoMd> lista = new ArrayList<InstrumentoMd>();

        InstrumentoMd Buscar = null;

        String sql = "", sql2 = "";

        //sql = "select INST_ID, INST_MODELO, INST_TIPO, INST_CARACT, (SELECT(CAT_DESCRIPCION) From CATEGORIA Where CAT_ID = i.Categoria_CAT_ID ) FROM INSTRUMENTO i ";
        sql = "select * FROM INSTRUMENTO";
        List <CategoriaMd> categorias = new ArrayList<CategoriaMd>();
        CategoriaDal buscarCategoriaPadre = new CategoriaDal();
        List<InstrumentoMd> lista2 = new ArrayList<InstrumentoMd>();
        categorias = buscarCategoriaPadre.buscaGrid();
        //+ filtro;

        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new InstrumentoMd();

                Buscar.setId(result.getString(1));           
                Buscar.setModelo(result.getString(2));
                Buscar.setTipo(result.getString(3));
                Buscar.setCaracteristicas(result.getString(4));
                Buscar.setCategoria(result.getString(5));
                lista.add(Buscar);
            }
            Buscar = null;
            for (InstrumentoMd instrumento:lista)
            {
                for (CategoriaMd categoria:categorias)
                {
                    if (instrumento.getCategoria().equals(categoria.getId())) {
                        Buscar = new InstrumentoMd();
                        Buscar.setId(instrumento.getId());
                        Buscar.setModelo(instrumento.getModelo());
                        Buscar.setTipo(instrumento.getTipo());
                        Buscar.setCaracteristicas(instrumento.getCaracteristicas());
                        Buscar.setCaracteristicas(categoria.getDescripcion());
                        lista2.add(Buscar);
                    }
                }
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
        return lista2;

    }

    public InstrumentoMd BuscarClientes(String nit) throws SQLException {
        PreparedStatement smt = null, smt2 = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null, r2;

        InstrumentoMd Buscar = null;

        String sql = "";

        sql = "select * FROM INSTRUMENTO where INST_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, nit);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new InstrumentoMd();

                Buscar.setId(result.getString(1));
                Buscar.setModelo(result.getString(2));
                Buscar.setTipo(result.getString(3));
                Buscar.setCaracteristicas(result.getString(4));
                Buscar.setCategoria(result.getString(5));
                
                

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
