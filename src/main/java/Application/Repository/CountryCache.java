package Application.Repository;

import Application.Models.Country;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryCache implements ICachable<Country> {

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
        ResultSet rs = CustomerQuery.selectAll("countries");
        while(rs.next()){
            cache.add(new Country(rs.getInt("Country_ID"),
                    rs.getString("Country")));
            /*
            if(ContactsCache.isCached && AppointmentsCache.isCached){

            }else{

            }
             */
        }
        isCached = true;
    }

    public ObservableList<Country> getCache() {
        return cache;
    }
}
