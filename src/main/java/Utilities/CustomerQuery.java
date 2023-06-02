package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery implements Queryable{

    public static String table = "customers";
    public static String[] fields = {"Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone", "Division_ID"};

    /*
    public static Appointment insert(String userName, String password) throws SQLException {
        String sql = "INSERT INTO "+table+" (User_Name, Password) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        return ps.executeUpdate();
    }

    public static Appointment update(int userId, String userName, String password) throws SQLException {
        String sql = "UPDATE "+table+" SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, userId);
        return ps.executeUpdate();
    }
     */

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
}
