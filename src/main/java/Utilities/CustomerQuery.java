package Utilities;

import Application.Models.Appointment;
import Application.Models.Customer;
import Application.Models.CustomerTable;

import java.sql.*;
import java.time.LocalDateTime;

public abstract class CustomerQuery extends Queryable implements IQueryable{

    public static String table = "customers";
    public static String[] fields = {"Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone", "Division_ID"};

    private static String fieldsToString(){
        String arrToStr = "";
        for (String s: fields) {
            if(s.equals("Division_ID")){ arrToStr += "customers.";}
            arrToStr += s+", ";
        }
        return arrToStr;
    }

    public static ResultSet selectAllCustomersView() throws SQLException {
        String sql = "SELECT "+fieldsToString()+" countries.Country, first_level_divisions.Division AS StateProvince, customers.Division_ID" +
                " FROM ("+table+" INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID)" +
                " INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID"; //WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    public static boolean delete(CustomerTable customer) {
        try{
            String sql = "DELETE FROM "+table+" WHERE "+table+".Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment "+customer.getCustomerName()+" (ID: "+customer.getCustomerId()+") has been successfully deleted");
                return true;
            }
            return false;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static int update(Customer customer) throws SQLException {
        String sql = "UPDATE "+table+" SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, " +
                "Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
        ps.setString(6, "App");
        ps.setInt(7, customer.getDivisionId());
        ps.setInt(8, customer.getCustomerId());
        return ps.executeUpdate();
    }

    public static int create(Customer customer) throws SQLException {
        try{
            String sql = "INSERT INTO "+table+" (Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,customer.getCustomerName());
            ps.setString(2,customer.getAddress());
            ps.setString(3,customer.getPostalCode());
            ps.setString(4,customer.getPhone());
            ps.setTimestamp(5, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
            ps.setString(6,"App");
            ps.setTimestamp(7, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
            ps.setString(8,"App");
            ps.setInt(9,customer.getDivisionId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                    return generatedId;
                }
            }
            return -1;
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            return -1;
        }
    }
}
