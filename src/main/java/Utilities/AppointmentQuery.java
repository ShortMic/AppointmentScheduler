package Utilities;

import Application.Models.Appointment;
import Application.Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AppointmentQuery implements Queryable{

    public static String table = "appointments";
    public static String[] fields = {"User_ID", "User_Name", "Password"};

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

    public static ResultSet selectAllApptView() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID" +
                " FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID"; //WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Filter by current user
        //ps.setInt(1, UserQuery.userID);
        return ps.executeQuery();
    }
}
