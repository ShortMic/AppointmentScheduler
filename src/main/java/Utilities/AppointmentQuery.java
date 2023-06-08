package Utilities;

import Application.Models.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AppointmentQuery extends Queryable implements IQueryable{

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
        String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID, appointments.Contact_ID" +
                " FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID"; //WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Filter by current user
        //ps.setInt(1, UserQuery.userID);
        return ps.executeQuery();
    }


    public static int update(Appointment appointment) throws SQLException {
        try{
            String sql = "INSERT INTO "+table+" (Title, Description, Location, Type, Start, End, Create_Date," +
                    "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,appointment.getTitle());
            ps.setString(2,appointment.getDescription());
            ps.setString(3,appointment.getLocation());
            ps.setString(4,appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8,"App");
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10,"App");
            ps.setInt(11,appointment.getCustomerId());
            ps.setInt(12,appointment.getUserId());
            ps.setInt(13,appointment.getContactId());
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
            return -1;
        }
    }//detailed_store1_inventory WHERE detailed_store1_inventory.film_id = OLD.film_id;

    public static boolean delete(Appointment appointment) throws SQLException {
        try{
            String sql = "DELETE FROM "+table+" WHERE "+table+".Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment.getAppointmentId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment "+appointment.getTitle()+" (ID: "+appointment.getAppointmentId()+") has been successfully deleted");
                return true;
            }
            return false;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }



}
