/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/entries";

    static final String USER = "root";
    static final String PASS = "root";

    public Connection openConnection() {
        Connection con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

