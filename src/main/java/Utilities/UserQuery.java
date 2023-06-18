package Utilities;

import Application.Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CustomerQuery abstract class is a utility for read and update operations on the MySQL database to populate the
 * CustomersCache.
 *
 * @author Michael Short
 * @version 1.0
 */
public abstract class UserQuery extends Queryable {

    public static String table = "users";
    public static String[] fields = {"User_ID", "User_Name", "Password"};
    public static int userID = 0;

    public static int insert(String userName, String password) throws SQLException {
        String sql = "INSERT INTO "+table+" (User_Name, Password) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        return ps.executeUpdate();
    }

    public static int update(int userId, String userName, String password) throws SQLException {
        String sql = "UPDATE "+table+" SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, userId);
        return ps.executeUpdate();
    }

    /**
     * Queries the user table in the database for a specific username.
     * @param userName the associated username to find
     * @param password the associated password to find
     * @return The user Id or -1 on a mismatch or failure
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static int select(String userName, String password) throws SQLException {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs =  ps.executeQuery();
        if(!rs.next()){
            System.out.println("Login Username and Password not found in User table!");
            return -1;
        }else{
            userID = rs.getInt("User_ID");
            System.out.println("Login Username "+userName+" and password match found! Logging in...");
            User.setCurrentUserId(userID);
            User.setCurrentUserName(rs.getString("User_Name"));
            User.setCurrentPassword(rs.getString("Password"));
            User.assign();
            return rs.getInt("User_ID");
        }
    }

}
