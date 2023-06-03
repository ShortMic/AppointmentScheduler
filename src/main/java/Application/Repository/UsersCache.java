package Application.Repository;

import Application.Models.User;
import Utilities.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersCache implements Cachable<User>{

    private static UsersCache instance;
    private ObservableList<User> cache;
    public FilteredList<User> filteredAppointments;
    public static boolean isCached = false;

    private UsersCache() throws SQLException {
        populateCache();
    }

    public static UsersCache getInstance() throws SQLException {
        if(instance == null){
            instance = new UsersCache();
        }
        return instance;
    }

    @Override
    public void populateCache() throws SQLException {
        cache = FXCollections.observableArrayList();
        ResultSet rs = UserQuery.selectAll("users");
        while(rs.next()){
            cache.add(new User(rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")));
            /*
            if(ContactsCache.isCached && AppointmentsCache.isCached){

            }else{

            }
             */
        }
        isCached = true;
    }

    @Override
    public ObservableList<User> getCache() {
        return cache;
    }
}
