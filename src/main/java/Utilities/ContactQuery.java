package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactQuery extends Queryable {

    public static String table = "contacts";
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

    public static String select(int contactId) throws SQLException {
        try{
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return rs.getString("Contact_Name");
            }else{
                System.out.println("Associated contactId not found in contact table!");
                throw new SQLException();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }

}
