package Application.Repository;

import Application.Models.User;
import Utilities.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UsersCache is a singleton like instanced local cache object meant to store the collection of User
 * type objects for access, reporting, deleting and editing purposes. Initially populates from the database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class UsersCache implements ICachable<User> {

    private static UsersCache instance;
    private ObservableList<User> cache;
    public FilteredList<User> filteredAppointments;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the UsersCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private UsersCache() throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the UsersCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the UsersCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static UsersCache getInstance() throws SQLException {
        if(instance == null){
            instance = new UsersCache();
        }
        return instance;
    }

    /**
     * Populates the User Cache from the MySQL Database, storing a collection of User objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    @Override
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = UserQuery.selectAll("users");
        while(rs.next()){
            cache.add(new User(rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")));
        }
        isCached = true;
    }

    /**
     * Accessor method for the local User collection cache
     * @return the Contact local cache
     */
    @Override
    public ObservableList<User> getCache() {
        return cache;
    }
}
