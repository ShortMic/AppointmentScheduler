package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DivisionLevelQuery abstract class is a utility for CRUD operations on the MySQL database to populate the
 * DivisionLevelCache.
 *
 * @author Michael Short
 * @version 1.0
 */
public abstract class DivisionLevelQuery extends Queryable {

    public static String table = "first_level_divisions";
    public static String[] fields = {"User_ID", "User_Name", "Password"};

}
