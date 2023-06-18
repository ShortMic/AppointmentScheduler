package Application.Repository;

import Application.Models.Customer;
import Application.Models.CustomerTable;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CustomersCache is a singleton like instanced local cache object meant to store the collection of Customer &
 * CustomerTable type objects for access, reporting, deleting and editing purposes. Initially populates from the
 * database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class CustomersCache implements ICachable<CustomerTable> {

    private static CustomersCache instance;
    private ObservableList<CustomerTable> cache;
    public FilteredList<Customer> filteredCountries;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the CustomersCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private CustomersCache() throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the CustomersCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the CustomersCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static CustomersCache getInstance() throws SQLException {
        if(instance == null){
            instance = new CustomersCache();
        }
        return instance;
    }

    /**
     * Populates the Customers Cache from the MySQL Database, storing a collection of CustomerTable objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = CustomerQuery.selectAllCustomersView();
        while(rs.next()){
            cache.add(new CustomerTable(rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getString("Country"),
                    rs.getString("StateProvince"),
                    rs.getInt("Division_ID")));
        }
        isCached = true;
    }

    /**
     * Accessor method for the local CustomerTable collection cache
     * @return the Appointment local cache
     */
    public ObservableList<CustomerTable> getCache() {
        return cache;
    }
}
