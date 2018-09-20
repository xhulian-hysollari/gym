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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
@ManagedBean(name = "SubscriptionBean")
@SessionScoped

public class Subscriptions {

    public String getSubscription_type_string() {
        return subscription_type_string;
    }

    public void setSubscription_type_string(String subscription_type_string) {
        this.subscription_type_string = subscription_type_string;
    }

    public String getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(String is_paid) {
        this.is_paid = is_paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(int subscription_type) {
        this.subscription_type = subscription_type;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public String getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(String ending_date) {
        this.ending_date = ending_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal_paid_by_client() {
        return total_paid_by_client;
    }

    public void setTotal_paid_by_client(Double total_paid_by_client) {
        this.total_paid_by_client = total_paid_by_client;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public int getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(int subscription_id) {
        this.subscription_id = subscription_id;
    }

    @ManagedProperty(value = "#{AuthenticationBean.db_username}")

    private String db_username;

    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private int subscription_type;
    private String starting_date;
    private String ending_date;
    private Double price;
    private Double total_paid_by_client;
    private boolean paid;
    private String created_by;
    private int subscription_id;
    private String status;
    private String is_paid;
    private String subscription_type_string;

    DB db = new DB();

    public List<Subscriptions> activeSubscriptions() throws ClassNotFoundException, ParseException {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try {

            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date = new Date();

            Date today = dateFormat.parse(dateFormat.format(date));

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            Statement stm = connection.createStatement();
            String sql = "Select * from subscriptions order by first_name";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Date end = dateFormat.parse(rs.getString("ending_date"));
                if (today.before(end)) {

                    Subscriptions subscription = new Subscriptions();
                    subscription.setSubscription_id(rs.getInt("id"));
                    subscription.setFirst_name(rs.getString("first_name"));
                    subscription.setLast_name(rs.getString("last_name"));
                    subscription.setEmail(rs.getString("email"));
                    subscription.setPhone_number(rs.getString("phone_number"));
                    subscription.setStarting_date(rs.getString("starting_date"));
                    subscription.setEnding_date(rs.getString("ending_date"));
                    subscription.setPaid(rs.getBoolean("paid"));
                    subscription.setIs_paid(rs.getBoolean("paid") == true ? "Paid" : "Unpaid");
                    subscription.setSubscription_type(rs.getInt("subscription_type"));

                    switch (rs.getInt("subscription_type")) {
                        case 1:
                            subscription.setSubscription_type_string("1 Month");
                            break;
                        case 2:
                            subscription.setSubscription_type_string("2 Months");
                            break;
                        case 3:
                            subscription.setSubscription_type_string("3 Months");
                            break;
                        case 4:
                            subscription.setSubscription_type_string("6 Months");
                            break;
                        case 5:
                            subscription.setSubscription_type_string("12 Months");
                            break;
                    }

                    subscription.setCreated_by(rs.getString("created_by"));
                    subscription.setStatus("Active");
                    subscription.setPrice(rs.getDouble("price"));
                    subscription.setTotal_paid_by_client(rs.getDouble("total_paid_by_client"));
                    subscriptions.add(subscription);

                    first_name = "";
                    last_name = "";
                    email = "";
                    phone_number = "";

                }
            }
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        return subscriptions;
    }

    public List<Subscriptions> finishedSubscriptions() throws ClassNotFoundException, ParseException {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try {

            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date = new Date();

            Date today = dateFormat.parse(dateFormat.format(date));

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            Statement stm = connection.createStatement();
            String sql = "Select * from subscriptions order by first_name";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Date end = dateFormat.parse(rs.getString("ending_date"));
                if (today.after(end)) {
                    Subscriptions subscription = new Subscriptions();
                    subscription.setSubscription_id(rs.getInt("id"));
                    subscription.setFirst_name(rs.getString("first_name"));
                    subscription.setLast_name(rs.getString("last_name"));
                    subscription.setEmail(rs.getString("email"));
                    subscription.setPhone_number(rs.getString("phone_number"));
                    subscription.setStarting_date(rs.getString("starting_date"));
                    subscription.setEnding_date(rs.getString("ending_date"));
                    subscription.setPaid(rs.getBoolean("paid"));
                    subscription.setIs_paid(rs.getBoolean("paid") == true ? "Paid" : "Unpaid");
                    subscription.setSubscription_type(rs.getInt("subscription_type"));

                    switch (rs.getInt("subscription_type")) {
                        case 1:
                            subscription.setSubscription_type_string("1 Month");
                            break;
                        case 2:
                            subscription.setSubscription_type_string("2 Months");
                            break;
                        case 3:
                            subscription.setSubscription_type_string("3 Months");
                            break;
                        case 4:
                            subscription.setSubscription_type_string("6 Months");
                            break;
                        case 5:
                            subscription.setSubscription_type_string("12 Months");
                            break;
                    }

                    subscription.setCreated_by(rs.getString("created_by"));
                    subscription.setStatus("Finished");
                    subscription.setPrice(rs.getDouble("price"));
                    subscription.setTotal_paid_by_client(rs.getDouble("total_paid_by_client"));
                    subscriptions.add(subscription);
                }
            }
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
        return subscriptions;
    }

    public void create() {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

            String start_date_formatted = formatter.format(formatter.parse(starting_date));

            Date starting_date_obj = formatter.parse(starting_date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(starting_date_obj);

            switch (subscription_type) {
                case 1:
                    price = 40.0;
                    cal.add(Calendar.MONTH, 1);
                    break;
                case 2:
                    price = 80.0;
                    cal.add(Calendar.MONTH, 2);
                    break;
                case 3:
                    price = 110.0;
                    cal.add(Calendar.MONTH, 3);
                    break;
                case 4:
                    price = 200.0;
                    cal.add(Calendar.MONTH, 6);
                    break;
                case 5:
                    price = 350.0;
                    cal.add(Calendar.MONTH, 12);
                    break;
            }

            String end_date_formatted = formatter.format(cal.getTime());

            if (paid == false) {
                total_paid_by_client = 0.0;
            } else {
                total_paid_by_client = price;
            }

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);
            String sql = "INSERT INTO subscriptions (first_name, last_name, email, phone_number, subscription_type, starting_date, ending_date, price, total_paid_by_client, paid, created_by) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone_number);
            preparedStatement.setInt(5, subscription_type);
            preparedStatement.setString(6, start_date_formatted);
            preparedStatement.setString(7, end_date_formatted);
            preparedStatement.setDouble(8, price);
            preparedStatement.setDouble(9, total_paid_by_client);
            preparedStatement.setBoolean(10, paid);
            preparedStatement.setString(11, db_username);
            preparedStatement.execute();

            first_name = "";
            last_name = "";
            email = "";
            phone_number = "";
            paid = false;
            subscription_type = 1;
            starting_date = "";
            ending_date = "";

        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            ex.getStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
    }

    public void view() throws ClassNotFoundException, ParseException {
        List<Subscriptions> subscriptions = activeSubscriptions();

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int new_subscription_id = Integer.parseInt(request.getParameter("subscription_id"));

        for (Subscriptions subscription : subscriptions) {

            if (subscription.getSubscription_id() == new_subscription_id) {

                this.setSubscription_id(subscription.subscription_id);
                this.setFirst_name(subscription.first_name);
                this.setLast_name(subscription.last_name);
                this.setEmail(subscription.email);
                this.setPhone_number(subscription.phone_number);
                this.setSubscription_type_string(subscription.subscription_type_string);
                this.setStarting_date(subscription.starting_date);
                this.setEnding_date(subscription.ending_date);
                this.setPrice(subscription.price);
                this.setPaid(subscription.paid);
                this.setIs_paid(subscription.is_paid);
                this.setTotal_paid_by_client(subscription.total_paid_by_client);
                this.setCreated_by(subscription.created_by);
            }
        }
    }

    public void renew() throws ClassNotFoundException, ParseException {
        List<Subscriptions> subscriptions = finishedSubscriptions();

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int new_subscription_id = Integer.parseInt(request.getParameter("subscription_id"));

        for (Subscriptions subscription : subscriptions) {

            if (subscription.getSubscription_id() == new_subscription_id) {

                this.setSubscription_id(subscription.subscription_id);
                this.setFirst_name(subscription.first_name);
                this.setLast_name(subscription.last_name);
                this.setEmail(subscription.email);
                this.setPhone_number(subscription.phone_number);
                this.setCreated_by(subscription.created_by);
                this.setTotal_paid_by_client(subscription.total_paid_by_client);
            }
        }
    }

    public void updateActive() throws ClassNotFoundException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
            int subscription_update_id = Integer.parseInt(request.getParameter("subscription_id"));
            String is_already_paid = request.getParameter("is_already_paid");

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);

            String sql = "Update subscriptions set first_name = ?, last_name = ?, email = ?, phone_number = ?, paid = ?, total_paid_by_client = ? where id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getFirst_name());
            preparedStatement.setString(2, this.getLast_name());
            preparedStatement.setString(3, this.getEmail());
            preparedStatement.setString(4, this.getPhone_number());
            if ("Paid".equals(is_already_paid)) {
                preparedStatement.setBoolean(5, true);
                preparedStatement.setDouble(6, this.getTotal_paid_by_client());
            } else {
                preparedStatement.setBoolean(5, this.isPaid());
                if (this.isPaid() == true) {
                    preparedStatement.setDouble(6, this.getTotal_paid_by_client() + this.getPrice());
                } else {
                    preparedStatement.setDouble(6, this.getPrice());
                }
            }

            preparedStatement.setInt(7, subscription_update_id);

            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Updated"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription not updated"));
            }

        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
        }
    }

    public void updateFinished() throws ClassNotFoundException, ParseException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
            int subscription_update_id = Integer.parseInt(request.getParameter("subscription_id"));

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.URL, db.UID, db.UI_PASSWORD);

            String sql = "Update subscriptions set first_name = ?, last_name = ?, email = ?, phone_number = ?, subscription_type = ?, starting_date = ?, ending_date = ?, price = ?, total_paid_by_client = ?, paid = ? where id = ?";

            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

            String start_date_formatted = formatter.format(formatter.parse(starting_date));

            Date starting_date_obj = formatter.parse(starting_date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(starting_date_obj);

            switch (subscription_type) {
                case 1:
                    price = 40.0;
                    cal.add(Calendar.MONTH, 1);
                    break;
                case 2:
                    price = 80.0;
                    cal.add(Calendar.MONTH, 2);
                    break;
                case 3:
                    price = 110.0;
                    cal.add(Calendar.MONTH, 3);
                    break;
                case 4:
                    price = 200.0;
                    cal.add(Calendar.MONTH, 6);
                    break;
                case 5:
                    price = 350.0;
                    cal.add(Calendar.MONTH, 12);
                    break;
            }

            String end_date_formatted = formatter.format(cal.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.getFirst_name());
            preparedStatement.setString(2, this.getLast_name());
            preparedStatement.setString(3, this.getEmail());
            preparedStatement.setString(4, this.getPhone_number());
            preparedStatement.setInt(5, subscription_type);
            preparedStatement.setString(6, start_date_formatted);
            preparedStatement.setString(7, end_date_formatted);
            preparedStatement.setDouble(8, price);
            if (paid == false) {
                preparedStatement.setDouble(9, total_paid_by_client);
            } else {
                if (total_paid_by_client == null) {
                    preparedStatement.setDouble(9, price);
                } else {
                    preparedStatement.setDouble(9, total_paid_by_client + price);
                }
            }
            preparedStatement.setBoolean(10, paid);

            preparedStatement.setInt(11, subscription_update_id);
            
            starting_date = "";
            subscription_type = 1;

            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription Renewed Successfully"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Subscription not renewed"));
            }

        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
        }
    }

}
