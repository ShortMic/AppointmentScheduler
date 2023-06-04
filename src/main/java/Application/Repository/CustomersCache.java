package Application.Repository;

import Application.Models.Customer;
import Application.Models.CustomerTable;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersCache implements Cachable<CustomerTable>{

    private static CustomersCache instance;
    private ObservableList<CustomerTable> cache;
    public FilteredList<Customer> filteredCountries;
    public static boolean isCached = false;

    private CustomersCache() throws SQLException {
        populateCache();
    }

    public static CustomersCache getInstance() throws SQLException {
        if(instance == null){
            instance = new CustomersCache();
        }
        return instance;
    }

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

    public ObservableList<CustomerTable> getCache() {
        return cache;
    }
}
