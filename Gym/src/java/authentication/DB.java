/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class DB {
    
    public String URL = "jdbc:mysql://localhost:3306/gym";
    public String UID = "root";
    public String UI_PASSWORD = "";
    public String LogUser;
            
    private Connection con;

    public void getConnected() {
        Connection con;
        try {
            setConnection(DriverManager.getConnection(URL, UID, UI_PASSWORD));

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public Connection getConnection() {
        return con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

}
