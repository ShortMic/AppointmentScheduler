package Application.Repository;

import Application.Models.AppointmentTable;
import Application.Models.Customer;
import Application.Models.CustomerTable;
import Utilities.AppointmentQuery;
import Utilities.ContactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsCache implements Cachable<CustomerTable>{

    private static ContactsCache instance;
    private ObservableList<CustomerTable> cache;
    public FilteredList<Customer> filteredCustomers;
    public static boolean isCached = false;

    private ContactsCache()throws SQLException {
        populateCache();
    }

    public static ContactsCache getInstance() throws SQLException {
        if(instance == null){
            instance = new ContactsCache();
        }
        return instance;
    }

    @Override
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
//        ResultSet rs = ContactQuery.selectAllApptView();
//        while(rs.next()){
//            cache.add(new AppointmentTable(rs.getInt("Appointment_ID"),
//                    rs.getString("Title"),
//                    rs.getString("Description"),
//                    rs.getString("Location"),
//                    rs.getString("Contact_Name"),
//                    rs.getString("Type"),
//                    rs.getDate("Start"),
//                    rs.getDate("End"),
//                    rs.getInt("Customer_ID"),
//                    rs.getInt("User_ID"),
//                    rs.getInt("Contact_ID")));
            /*
            if(ContactsCache.isCached && AppointmentsCache.isCached){

            }else{

            }
             */
        //}
        isCached = true;
    }

    @Override
    public ObservableList<CustomerTable> getCache() {
        return cache;
    }
}
