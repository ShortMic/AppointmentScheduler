package Application.Repository;

import Application.Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class CustomersCache {

    private ObservableList<Customer> customers;
    public FilteredList<Customer> filteredCustomers;
    public static boolean isCached = false;

    public CustomersCache(){
        customers = FXCollections.observableArrayList();
    }
}
