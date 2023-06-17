package Application.Repository;

import Application.Models.Contact;
import Utilities.ContactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsCache implements ICachable<Contact> {

    private static ContactsCache instance;
    private ObservableList<Contact> cache;
    public FilteredList<Contact> filteredContacts;
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
        ResultSet rs = ContactQuery.selectAll("contacts");
        while(rs.next()){
            cache.add(new Contact(rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")));
        }
        isCached = true;
    }

    @Override
    public ObservableList<Contact> getCache() {
        return cache;
    }
}
