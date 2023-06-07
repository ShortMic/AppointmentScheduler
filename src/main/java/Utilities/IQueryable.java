package Utilities;

import java.sql.SQLException;

public interface IQueryable {

    static int insert(String userName, String password) throws SQLException {
        return 0;
    }

    static <T> int update(T o) throws SQLException {
        return 0;
    }

    static int select(String userName, String password) throws SQLException {
        return 0;
    }

    static int select(String searchToken) throws SQLException {
        return 0;
    }

    static int select(int id) throws SQLException {
        return 0;
    }

    static int delete(String userName, String password) throws SQLException {
        return 0;
    }

}
