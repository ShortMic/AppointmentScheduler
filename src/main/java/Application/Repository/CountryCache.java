package Application.Repository;

import Application.Models.Country;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CountryCache is a singleton like instanced local cache object meant to store the collection of Country
 * type objects for access, reporting, deleting and editing purposes. Initially populates from the database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class CountryCache implements ICachable<Country> {

    private static CountryCache instance;
    private ObservableList<Country> cache;
    public FilteredList<Country> filteredCountry;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the CountryCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private CountryCache() throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the CountryCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the CountryCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static CountryCache getInstance() throws SQLException {
        if(instance == null){
            instance = new CountryCache();
        }
        return instance;
    }

    /**
     * Populates the Country Cache from the MySQL Database, storing a collection of Country objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = CustomerQuery.selectAll("countries");
        while(rs.next()){
            cache.add(new Country(rs.getInt("Country_ID"),
                    rs.getString("Country")));
        }
        isCached = true;
    }

    /**
     * Accessor method for the local Contact collection cache
     * @return the Contact local cache
     */
    public ObservableList<Country> getCache() {
        return cache;
    }
}
