/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */

@ManagedBean(name="AuthenticationBean")
@SessionScoped

public class Authentication {

    DB db = new DB(); 
    
    private String username;
    private String password;  
    private String db_username;
    private String db_password; 
    private String role;
    private int id;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
    public String login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            String sql = "Select * FROM users WHERE username = '"+username+"'";
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            db_username = rs.getString(4);
            role = rs.getString(6);
            db_password = rs.getString(5);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        if (username.equalsIgnoreCase(db_username)) {
            if (password.equals(db_password))
                return "main?faces-redirect=true";
            else  {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please check your password"));
                return null;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please check your username"));
        return null;
    }
    
    public String logout() {
        HttpSession logout = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        logout.invalidate();
        return "/index?faces-redirect=true";
    } 
}
