package Application.Repository;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Application.Models.CustomerTable;
import Application.Models.DivisionLevel1;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.SQLException;

public class DivisionLevelCache implements Cachable<DivisionLevel1>{

    private static DivisionLevelCache instance;
    private ObservableList<DivisionLevel1> cache;
    public FilteredList<DivisionLevel1> filteredAppointments;
    public static boolean isCached = false;

    private DivisionLevelCache() throws SQLException {
        populateCache();
    }

    @Override
    public void populateCache() throws SQLException {

    }

    @Override
    public ObservableList<DivisionLevel1> getCache() {
        return cache;
    }
}
