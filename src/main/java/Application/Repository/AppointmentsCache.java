package Application.Repository;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Utilities.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsCache implements Cachable<AppointmentTable>{

    private static AppointmentsCache instance;
    private ObservableList<AppointmentTable> cache;
    public FilteredList<Appointment> filteredAppointments;
    public static boolean isCached = false;

    private AppointmentsCache() throws SQLException {
        populateCache();
    }

    public static AppointmentsCache getInstance() throws SQLException {
        if(instance == null){
            instance = new AppointmentsCache();
        }
        return instance;
    }

    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = AppointmentQuery.selectAllApptView();
        while(rs.next()){
            cache.add(new AppointmentTable(rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Contact_Name"),
                    rs.getString("Type"),
                    rs.getDate("Start"),
                    rs.getDate("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
            /*
            if(ContactsCache.isCached && AppointmentsCache.isCached){

            }else{

            }
             */
        }
        isCached = true;
    }

    public ObservableList<AppointmentTable> getCache() {
        return cache;
    }

}
