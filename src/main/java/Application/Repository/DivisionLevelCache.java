package Application.Repository;

import Application.Models.DivisionLevel1;
import Utilities.DivisionLevelQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionLevelCache implements ICachable<DivisionLevel1> {

    private static DivisionLevelCache instance;
    private ObservableList<DivisionLevel1> cache;
    public FilteredList<DivisionLevel1> filteredAppointments;
    public static boolean isCached = false;

    private DivisionLevelCache() throws SQLException {
        populateCache();
    }

    public static DivisionLevelCache getInstance() throws SQLException {
        if(instance == null){
            instance = new DivisionLevelCache();
        }
        return instance;
    }

    @Override
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = DivisionLevelQuery.selectAll("first_level_divisions");
        while(rs.next()){
            cache.add(new DivisionLevel1(rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")));
            /*
            if(ContactsCache.isCached && AppointmentsCache.isCached){

            }else{

            }
             */
        }
        isCached = true;
    }

    @Override
    public ObservableList<DivisionLevel1> getCache() {
        return cache;
    }
}
