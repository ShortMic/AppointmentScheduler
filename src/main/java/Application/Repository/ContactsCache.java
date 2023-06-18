package Application.Repository;

import Application.Models.Contact;
import Utilities.ContactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ContactsCache is a singleton like instanced local cache object meant to store the collection of Contacts
 * type objects for access, reporting, deleting and editing purposes. Initially populates from the database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class ContactsCache implements ICachable<Contact> {

    private static ContactsCache instance;
    private ObservableList<Contact> cache;
    public FilteredList<Contact> filteredContacts;
    public static boolean isCached = false;

    /**
     * Private constructor only used within getInstance if the ContactCache has not been initialized
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private ContactsCache()throws SQLException {
        populateCache();
    }

    /**
     * Creates or supplies the ContactsCache. Acts as a public accessor for the cache.
     * @return The singleton like instance of the ContactsCache
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public static ContactsCache getInstance() throws SQLException {
        if(instance == null){
            instance = new ContactsCache();
        }
        return instance;
    }

    /**
     * Populates the Contacts Cache from the MySQL Database, storing a collection of Contact objects
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
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

    /**
     * Accessor method for the local Contact collection cache
     * @return the Contact local cache
     */
    @Override
    public ObservableList<Contact> getCache() {
        return cache;
    }
}
