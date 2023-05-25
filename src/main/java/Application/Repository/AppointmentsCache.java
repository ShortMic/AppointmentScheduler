package Application.Repository;

import Application.Models.Appointment;
import Utilities.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsCache {

    private ObservableList<Appointment> appointments;
    public FilteredList<Appointment> filteredAppointments;
    //private ObservableList<> appointments;
    public AppointmentsCache() throws SQLException {
        appointments = FXCollections.observableArrayList();
        ResultSet rs = AppointmentQuery.selectAllApptView();
        while(rs.next()){
            appointments.add(new Appointment(rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getDate("Start"),
                    rs.getDate("End"),
                    rs.getInt("Appointment_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
        }
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }
}
