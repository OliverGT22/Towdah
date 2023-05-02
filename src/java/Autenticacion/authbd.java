/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Autenticacion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class authbd {

    public String autUserStr(String user, String pass) {

        String result = "Error";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String dato = "jdbc:mysql://142.93.12.121:3306/marex";

            conn = DriverManager.getConnection(dato, user, pass);

            if (conn != null) {
                result = "ok";
            }

        } catch (ClassNotFoundException e) {
            return result;
        } catch (SQLException e) {
            return result;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e2) {
                return result;
            }
        }
        return result;
    }
}
