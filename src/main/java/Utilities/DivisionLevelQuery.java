package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionLevelQuery extends Queryable {

    public static String table = "first_level_divisions";
    public static String[] fields = {"User_ID", "User_Name", "Password"};

    public static ResultSet selectAllApptView() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID, appointments.Contact_ID" +
                " FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID"; //WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Filter by current user
        //ps.setInt(1, UserQuery.userID);
        return ps.executeQuery();
    }
}
