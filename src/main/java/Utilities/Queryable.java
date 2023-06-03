package Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Queryable implements IQueryable{

    public static ResultSet selectAll(String table) throws SQLException {
        String sql = "SELECT * FROM "+table;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    }
}
