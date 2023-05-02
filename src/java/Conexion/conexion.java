package Conexion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class conexion {

    Connection conn = null;

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String dato = "jdbc:mysql://142.93.12.121:3306/Capacitaciones";

            conn = DriverManager.getConnection(dato, "marex", "Manager00");

            return conn;

        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return conn;
        }
    }

    public Connection getConnection() {
        return conexion();
    }

    public void desconectar() throws SQLException {
        conn.close();
        conn = null;

    }

}
