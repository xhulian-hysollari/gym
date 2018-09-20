/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employees;

import authentication.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author user
 */

@ManagedBean(name = "Employees")
@SessionScoped

public class Employees {

    @ManagedProperty(value = "#{AuthenticationBean.role}")
    
    private String role;
    
    private String username;
    private String password; 
    private String confirmPassword;
    private String first_name;
    private String last_name; 
    private int user_id;
    
    DB db = new DB(); 
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
     
    public List<Employees> employees() throws ClassNotFoundException {
        List<Employees> employees = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            Statement stm = connection.createStatement();
            String sql = "Select * from users where `role` = 'Employee'";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Employees employee = new Employees();
                employee.setUser_id(rs.getInt("id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setUsername(rs.getString("username"));
                employee.setRole("Employee");
                employees.add(employee);
            }
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        return employees;
    } 
    
     public void create() {
        try {
            if (password.equals(confirmPassword)) {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
                String sql = "INSERT INTO users (first_name, last_name, username, password, role) VALUES (?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, first_name);
                pst.setString(2, last_name);
                pst.setString(3, username);
                pst.setString(4, password);
                pst.setString(5, "Employee");
                pst.execute();
                
                first_name = "";
                last_name = "";
                username = "";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password and Confirm Password must be equal"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
    }
    
    public void edit() throws ClassNotFoundException {
            
        List<Employees> employees = employees();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int employee_id = Integer.parseInt(request.getParameter("id"));

        for (Employees employee : employees) {

            if (employee.getUser_id() == employee_id) {
                this.setUser_id(employee.user_id);
                this.setUsername(employee.username);
                this.setFirst_name(employee.first_name);
                this.setLast_name(employee.last_name);
                this.setRole(employee.role);              
            }
        }
    }
    
    public void update() throws ClassNotFoundException {
        try {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int user_update_id = Integer.parseInt(request.getParameter("user_id"));
       
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            
            String sql = "Update users set first_name = ?, last_name = ?, username = ? where id = ?";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, this.getFirst_name());
            pst.setString(2, this.getLast_name());
            pst.setString(3, this.getUsername());
            pst.setInt(4, user_update_id);
                        
            int count = pst.executeUpdate();
            if (count > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Successfully Updated"));
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something Went Wrong"));
            }

        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
        }
    }

    public void delete() throws ClassNotFoundException, SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int user_delete_id = Integer.parseInt(request.getParameter("delete_id"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            String sql = "Delete from users where id = " + user_delete_id + "";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee Deleted Successfully"));
        } catch (ClassNotFoundException | SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
        }
    }
  
}
