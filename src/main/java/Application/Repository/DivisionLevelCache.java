package Application.Repository;

import Application.Models.DivisionLevel1;
import Utilities.DivisionLevelQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DivisionLevelCache is a singleton like instanced local cache object meant to store the collection of Country
 * type objects for access, reporting, deleting and editing purposes. Initially populates from the database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class DivisionLevelCache implements ICachable<DivisionLevel1> {

    private static DivisionLevelCache instance;
    private ObservableList<DivisionLevel1> cache;
    public FilteredList<DivisionLevel1> filteredAppointments;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the DivisionLevelCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private DivisionLevelCache() throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the DivisionLevelCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the DivisionLevelCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static DivisionLevelCache getInstance() throws SQLException {
        if(instance == null){
            instance = new DivisionLevelCache();
        }
        return instance;
    }

    /**
     * Populates the DivisionLevel1 Cache from the MySQL Database, storing a collection of DivisionLevel1 objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    @Override
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = DivisionLevelQuery.selectAll("first_level_divisions");
        while(rs.next()){
            cache.add(new DivisionLevel1(rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")));
        }
        isCached = true;
    }

    /**
     * Accessor method for the local DivisionLevel1 collection cache
     * @return the Contact local cache
     */
    @Override
    public ObservableList<DivisionLevel1> getCache() {
        return cache;
    }
}
