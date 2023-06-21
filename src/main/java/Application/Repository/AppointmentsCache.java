package Application.Repository;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Utilities.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The AppointmentsCache is a singleton like instanced local cache object meant to store the collection of Appointment &
 * AppointmentTable type objects for access, reporting, deleting and editing purposes. Initially populates from the
 * database and has schedule error validation.
 *
 * @author Michael Short
 * @version 1.0
 */
public class AppointmentsCache implements ICachable<AppointmentTable> {

    private static AppointmentsCache instance;
    private ObservableList<AppointmentTable> cache;
    public FilteredList<Appointment> filteredAppointments;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the AppointmentCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private AppointmentsCache() throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the AppointmentCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the AppointmentsCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static AppointmentsCache getInstance() throws SQLException {
        if(instance == null){
            instance = new AppointmentsCache();
        }
        return instance;
    }

    /**
     * Populates the Appointments Cache from the MySQL Database, storing a collection of AppointmentTable objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
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
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
        }
        isCached = true;
    }

    /**
     * Accessor method for the local AppointmentTable collection cache
     * @return the Appointment local cache
     */
    public ObservableList<AppointmentTable> getCache() {
        return cache;
    }

    /**
     * Provides an error handling flag for any specific LocalDateTime ranges to see if the range conflicts with
     * pre-existing appointments in the collection. Uses a lambda function to return a bool of whether any appointments
     * fall under the conflicting time slot criteria.
     * @param start The start appointment LocalDateTime range
     * @param end The end appointment LocalDateTime range
     * @return boolean of if the time range conflicts with a pre-existing schedule
     */
    public boolean timeSlotConflict(LocalDateTime start, LocalDateTime end){
        return !cache.stream().allMatch(x -> ((start.isAfter(x.getEnd()) || start.isEqual(x.getEnd())) && end.isAfter(x.getEnd())
                || ((end.isBefore(x.getStart()) || end.isEqual(x.getStart())) && start.isBefore(x.getEnd()))));
    }

    /**
     * Provides an error handling flag for any specific LocalDateTime ranges to see if the range conflicts with
     * pre-existing appointments in the collection. Excludes the id argument passed in on the basis of the associated
     * appointment is the one being edited.
     * @param start The start appointment LocalDateTime range
     * @param end The end appointment LocalDateTime range
     * @param id The id associated with the appointment time range to exclude
     * @return boolean of if the time range conflicts with a pre-existing schedule
     */
    public boolean timeSlotConflict(LocalDateTime start, LocalDateTime end, int id){
        return !cache.stream().allMatch(x -> {
            if(x.getAppointmentId() != id) {
                return ((start.isAfter(x.getEnd()) || start.isEqual(x.getEnd())) && end.isAfter(x.getEnd())
                        || ((end.isBefore(x.getStart()) || end.isEqual(x.getStart())) && start.isBefore(x.getEnd())));
            }else{
                return true;
            }
        });
    }

}
