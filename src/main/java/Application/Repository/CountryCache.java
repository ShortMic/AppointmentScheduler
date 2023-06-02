package Application.Repository;

import Application.Models.AppointmentTable;
import Application.Models.Country;
import Application.Models.Customer;
import Application.Models.CustomerTable;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryCache implements Cachable<Country>{

    private static CountryCache instance;
    private ObservableList<Country> cache;
    public FilteredList<Country> filteredCountry;
    public static boolean isCached = false;

    private CountryCache() throws SQLException {
        populateCache();
    }

    public static CountryCache getInstance() throws SQLException {
        if(instance == null){
            instance = new CountryCache();
        }
        return instance;
    }

    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = CustomerQuery.selectAllCustomersView();
//        while(rs.next()){
//            cache.add(new CustomerTable(rs.getInt("Customer_ID"),
//                    rs.getString("Customer_Name"),
//                    rs.getString("Address"),
//                    rs.getString("Postal_Code"),
//                    rs.getString("Phone"),
//                    rs.getString("Country"),
//                    rs.getString("StateProvince"),
//                    rs.getInt("Division_ID")));
//            /*
//            if(ContactsCache.isCached && AppointmentsCache.isCached){
//
//            }else{
//
//            }
//             */
//        }
        isCached = true;
    }

    public ObservableList<Country> getCache() {
        return cache;
    }
}
