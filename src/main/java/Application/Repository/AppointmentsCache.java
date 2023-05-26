package Application.Repository;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Utilities.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsCache {

    public ObservableList<AppointmentTable> appointmentsTable;
    public FilteredList<Appointment> filteredAppointments;
    public static boolean isCached = false;
    //private ObservableList<> appointments;
    public AppointmentsCache() throws SQLException {
        appointmentsTable = FXCollections.observableArrayList();
        ResultSet rs = AppointmentQuery.selectAllApptView();
        while(rs.next()){
            appointmentsTable.add(new AppointmentTable(rs.getInt("Appointment_ID"),
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

    public ObservableList<AppointmentTable> getAppointments() {
        return appointmentsTable;
    }

}
