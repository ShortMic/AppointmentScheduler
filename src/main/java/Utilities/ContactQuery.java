package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ContactQuery abstract class is a utility for CRUD operations on the MySQL database to populate the
 * ContactCache.
 *
 * @author Michael Short
 * @version 1.0
 */
public abstract class ContactQuery extends Queryable {

    public static String table = "contacts";
    public static String[] fields = {"User_ID", "User_Name", "Password"};

    /**
     * Queries the contact table in the database for a specific contact id.
     * @param contactId the Associated contact id to find
     * @return The contact name associated with id or an empty string
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
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
