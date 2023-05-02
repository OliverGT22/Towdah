package Datos;

import Modelos.UsuarioMd;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.conexion;
import Utilitarios.Util;
import Utilitarios.Utilitarios;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;

public class UsuarioDal {

    Utilitarios ut = new Utilitarios();
//

    public int Crear(UsuarioMd EmpleadosDAL) throws SQLException {
        {
            PreparedStatement smt = null, smt2 = null, smt3 = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
            int result = 0, correctas = 0;

            try {
                String sql = "insert into CTRL_USUARIOS values ((SELECT IFNULL(MAX(USR_CODIGO), 0)+1 FROM CTRL_USUARIOS u),?,?,?,?,?,now(),null,'C',?,?)";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                smt.setString(1, EmpleadosDAL.getUsrUsuario());
                smt.setString(2, EmpleadosDAL.getUsrRol());
                smt.setString(3, EmpleadosDAL.getUsrUsuarioCrea());
                smt.setString(4, EmpleadosDAL.getUsrNombre());
                smt.setString(5, ut.encriptar(EmpleadosDAL.getUsrPalabra(), "x55s88d"));
                smt.setString(6, EmpleadosDAL.getContacto());
                smt.setString(7, ut.encriptar("secreto", "x55s88d"));

                result = smt.executeUpdate();
                correctas += result;

                sql = "CREATE USER ?@'%' IDENTIFIED BY ?";
                smt2 = conn.prepareStatement(sql);
                smt2.setString(1, EmpleadosDAL.getUsrUsuario());
                smt2.setString(2, "secreto");

                result = smt2.executeUpdate();

                sql = "GRANT ALL PRIVILEGES ON *.* TO ?@'%'";
                smt3 = conn.prepareStatement(sql);
                smt3.setString(1, EmpleadosDAL.getUsrUsuario());

                result = smt3.executeUpdate();

                if (correctas == 1) {
                    conn.commit();
                } else {
                    conn.rollback();
                }

                return correctas;

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

    public int CambioClave(UsuarioMd EmpleadosDAL) throws SQLException {
        {
            PreparedStatement smt = null, smt2 = null, smt3 = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
            int result = 0, correctas = 0;

            try {
                String sql = "update CTRL_USUARIOS set USR_USUARIO_CREA = ?, USR_PALABRA= ?,USR_STATUS ='A', USR_FECHA_MODIFICA = now(), USR_CLAVE = ? where Upper(usr_usuario) =?";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                smt.setString(1, EmpleadosDAL.getUsrUsuarioCrea());
                smt.setString(2, ut.encriptar(EmpleadosDAL.getUsrPalabra(), "x55s88d"));
                smt.setString(3, ut.encriptar(EmpleadosDAL.getUsrClave(), "x55s88d"));
                smt.setString(4, EmpleadosDAL.getUsrUsuario());

                result = smt.executeUpdate();
                correctas += result;

                sql = "SET PASSWORD FOR ?@'%' = PASSWORD(?)";
                smt2 = conn.prepareStatement(sql);
                smt2.setString(1, EmpleadosDAL.getUsrUsuario());
                smt2.setString(2, EmpleadosDAL.getUsrClave());

                result = smt2.executeUpdate();

                if (correctas == 1) {
                    conn.commit();
                } else {
                    conn.rollback();
                }

                return correctas;

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

    public int RestaurarClave(UsuarioMd EmpleadosDAL) throws SQLException {
        {
            PreparedStatement smt = null, smt2 = null, smt3 = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
            int result = 0, correctas = 0;

            try {
                String sql = "update CTRL_USUARIOS set USR_USUARIO_CREA = ?, USR_STATUS ='C', USR_FECHA_MODIFICA = now(),USR_CLAVE = ? where Upper(usr_usuario) =?";

                conn.setAutoCommit(false);

                smt = conn.prepareStatement(sql);
                smt.setString(1, EmpleadosDAL.getUsrUsuarioCrea());
                smt.setString(2, ut.encriptar("secreto", "x55s88d"));
                smt.setString(3, EmpleadosDAL.getUsrUsuario());

                result = smt.executeUpdate();
                correctas += result;

                sql = "SET PASSWORD FOR ?@'%' = PASSWORD(?)";
                smt2 = conn.prepareStatement(sql);
                smt2.setString(1, EmpleadosDAL.getUsrUsuario());
                smt2.setString(2, "secreto");

                result = smt2.executeUpdate();

                if (correctas == 1) {
                    conn.commit();
                } else {
                    conn.rollback();
                }

                return correctas;

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

    public int ModificarUsuario(UsuarioMd EmpleadosDAL) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            conexion conex = new conexion();
            conn = conex.getConnection();
            int result = 0;

            try {
                String sql = "update CTRL_USUARIOS set USR_NOMBRE = ?, USR_ROL = ?, USR_USUARIO_CREA = ?, USR_STATUS =?, USR_FECHA_MODIFICA = now(), USR_CONTACTO = ? where Upper(usr_usuario) =?";

                smt = conn.prepareStatement(sql);
                smt.setString(1, EmpleadosDAL.getUsrNombre());
                smt.setString(2, EmpleadosDAL.getUsrRol());
                smt.setString(3, EmpleadosDAL.getUsrUsuarioCrea());
                smt.setString(4, EmpleadosDAL.getUsrStatus());
                smt.setString(5, EmpleadosDAL.getContacto());
                smt.setString(6, EmpleadosDAL.getUsrUsuario());

                result = smt.executeUpdate();

                return result;

            } catch (Exception e) {

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

    public UsuarioMd busca(String pUsuario) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        conexion conex = new conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        UsuarioMd Buscar = null;

        String sql = "Select * from CTRL_USUARIOS WHERE UPPER(USR_USUARIO) = ? ";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, pUsuario);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new UsuarioMd();

                Buscar.setUsrUsuario(result.getString(2));
                Buscar.setUsrRol(result.getString(3));
                Buscar.setUsrNombre(result.getString(5));
                Buscar.setUsrPalabra(result.getString(6));
                Buscar.setUsrStatus(result.getString(9));
                Buscar.setContacto(result.getString(10));
                Buscar.setUsrClave(result.getString(11));
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
