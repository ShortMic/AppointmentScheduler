package Utilities;

import java.sql.SQLException;

public interface IQueryable {

    static String table = null;
    static String[] fields = null;

    static int insert(String userName, String password) throws SQLException {
        return 0;
    }

    static int update(String userName, String password) throws SQLException {
        return 0;
    }

    static int select(String userName, String password) throws SQLException {
        return 0;
    }

    static int delete(String userName, String password) throws SQLException {
        return 0;
    }

}
