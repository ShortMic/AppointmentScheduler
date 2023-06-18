package Utilities;

import Application.Models.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The AppointmentQuery abstract class is a utility for CRUD operations on the MySQL database to populate the
 * AppointmentCache.
 *
 * @author Michael Short
 * @version 1.0
 */
public abstract class AppointmentQuery extends Queryable implements IQueryable{

    public static String table = "appointments";
    public static String[] fields = {"Appointment_ID", "Title", "Description", "Location", "Contact_Name", "Type", "Start", "End", "Customer_ID", "User_ID"};

    /**
     * Queries the Appointment Table in the MySQL database for all records as well as the additional external table
     * fields required for the GUI Appointment table display and AppointmentTable type objects.
     *
     * @return The result set from the query
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static ResultSet selectAllApptView() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID, appointments.Contact_ID" +
                " FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Filter by current user
        //ps.setInt(1, UserQuery.userID);
        return ps.executeQuery();
    }

    /**
     * Queries and updates all the fields with the associated Appointment record in the Appointment Table of the MySQL database.
     * @param appointment The associated appointment object to update in the database
     * @return the number of rows updated
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static int update(Appointment appointment) throws SQLException {
        String sql = "UPDATE "+table+" SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, " +
                "Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getUTCStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getUTCEnd()));
        ps.setTimestamp(7, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
        ps.setString(8, "App");
        ps.setInt(9, appointment.getCustomerId());
        ps.setInt(10, appointment.getUserId());
        ps.setInt(11, appointment.getContactId());
        ps.setInt(12, appointment.getAppointmentId());
        return ps.executeUpdate();
    }

    /**
     * Queries & creates a new record from an appointment object in the Appointment Table of the MySQL database.
     * @param appointment The associated appointment object to insert into the database
     * @return the number of rows affected
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static int create(Appointment appointment) throws SQLException {
        try{
            String sql = "INSERT INTO "+table+" (Title, Description, Location, Type, Start, End, Create_Date," +
                    "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,appointment.getTitle());
            ps.setString(2,appointment.getDescription());
            ps.setString(3,appointment.getLocation());
            ps.setString(4,appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getUTCStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getUTCEnd()));
            ps.setTimestamp(7, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
            ps.setString(8,"App");
            ps.setTimestamp(9, Timestamp.valueOf(TimeConverter.convertToUTC(LocalDateTime.now()).toLocalDateTime()));
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
            System.out.println(exception.getMessage());
            return -1;
        }
    }

    /**
     * Queries & deletes the associated Appointment record from the appointment table of the MySQL database.
     * @param appointment The associated appointment object to delete from the database
     * @return a boolean of if the delete was successful
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
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
