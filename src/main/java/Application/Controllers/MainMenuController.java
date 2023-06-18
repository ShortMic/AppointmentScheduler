package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.*;
import Application.Repository.*;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import Utilities.TimeConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The MainMenuController which is responsible for the logic behind MainMenuView. It helps provide the data for the
 * displayed tables for the GUI as well as buttons for functionality and routing to add, modify and reporting screens.
 * Provides robust error handling and local machine time conversion from UTC timestamps directly from the cache/database.
 *
 * @author Michael Short
 * @version 1.0
 */
public class MainMenuController implements Initializable{

    @FXML
    public Text appTitleLabel;
    @FXML
    public Tab appointmentTab;
    @FXML public TableView<AppointmentTable> appointmentTable;
    @FXML public TableColumn<AppointmentTable, Integer> appointmentIdCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentTitleCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentDescriptionCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentLocationCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentContactCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentTypeCol;
    @FXML public TableColumn<AppointmentTable, LocalDateTime> appointmentStartCol;
    @FXML public TableColumn<AppointmentTable, LocalDateTime> appointmentEndCol;
    @FXML public TableColumn<AppointmentTable, Integer> appointmentCustomerIdCol;
    @FXML public TableColumn<AppointmentTable, Integer> appointmentUserIdCol;
    @FXML
    public Button addAppointmentBtn;
    @FXML
    public Button modifyAppointmentBtn;
    @FXML
    public Button deleteAppointmentBtn;
    @FXML
    public TableView<CustomerTable> customerTable;
    @FXML public TableColumn<CustomerTable, Integer> customerIdCol;
    @FXML public TableColumn<CustomerTable, String> customerNameCol;
    @FXML public TableColumn<CustomerTable, String> customerAddressCol;
    @FXML public TableColumn<CustomerTable, String> customerPostalCodeCol;
    @FXML public TableColumn<CustomerTable, String> customerPhoneCol;
    @FXML public TableColumn<CustomerTable, String> customerCountryCol;
    @FXML public TableColumn<CustomerTable, String> customerStateProvinceCol;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Button modifyCustomerBtn;
    @FXML
    public Button deleteCustomerBtn;
    @FXML
    public Button reportsBtn;
    @FXML
    public Button exitBtn;
    public AppointmentsCache appointmentsCache;
    public CustomersCache customersCache;
    public ContactsCache contactsCache;
    public CountryCache countryCache;
    public DivisionLevelCache divisionLevelCache;
    public UsersCache usersCache;
    public Label appointmentTablePlaceholderLabel;
    public Label customerTablePlaceholderLabel;
    public static boolean isInitialLogin = false;
    public RadioButton viewAllRadioBtn;
    public ToggleGroup appointmentFilterBtns;
    public RadioButton viewMonthRadioBtn;
    public RadioButton viewWeekRadioBtn;
    private Alert alert;
    private boolean appointmentTableInitialized = false;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsCache = AppointmentsCache.getInstance();
            customersCache = CustomersCache.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateAppointmentsTable();
        populateCustomersTable();
        populateLocalCaches();
        if(isInitialLogin){
            int userId = User.getCurrentUserId();
            AppointmentTable appointmentStartIn15 = appointmentsCache.getCache().stream().filter(x -> x.getUserId() == userId &&
                    (LocalDateTime.now().isAfter(x.getStart().minusMinutes(15))
                            && (LocalDateTime.now().isBefore(x.getStart())))).findFirst().orElse(null);
            if(appointmentStartIn15 != null){
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "You an upcoming appointment ("+appointmentStartIn15.getTitle()+
                        " ID: "+appointmentStartIn15.getAppointmentId()+") for "+appointmentStartIn15.getStart().toString()+".");
                alert.setHeaderText("Upcoming Appointment");
                alert.show();
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "You have no upcoming appointments.");
                alert.setHeaderText("Upcoming Appointment");
                alert.show();
            }
            isInitialLogin = false;
        }
    }

    /**
     * Private helper method that populates the GUI's Appointment Table view based on the current filter selection and
     * data pulled from the local cache.
     */
    private void populateAppointmentsTable(){
        if(!appointmentTableInitialized){
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("appointmentId"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("location"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("contactName"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, LocalDateTime>("start"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, LocalDateTime>("end"));
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("customerId"));
            appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("userId"));
            appointmentTableInitialized = true;
        }
        if(viewMonthRadioBtn.isSelected()){
            ObservableList<AppointmentTable> appointmentList = FXCollections.observableList(
                    appointmentsCache.getCache().stream()
                            .filter(x -> x.getStart().getYear() == LocalDateTime.now().getYear()
                                    && x.getStart().getMonth().equals(LocalDateTime.now().getMonth())).toList());
            appointmentTable.setItems(appointmentList);
        }else if(viewWeekRadioBtn.isSelected()){
            ObservableList<AppointmentTable> appointmentList = FXCollections.observableList(
                    appointmentsCache.getCache().stream()
                            .filter(x -> x.getStart().getYear() == LocalDateTime.now().getYear()
                                    && TimeConverter.getWeekNumber(x.getStart()) ==
                                    TimeConverter.getWeekNumber(LocalDateTime.now())).toList());
            appointmentTable.setItems(appointmentList);
        }else{
            appointmentTable.setItems(appointmentsCache.getCache());
        }
    }

    /**
     * Private helper method that populates the GUI's Customer Table view based on data pulled from the local cache.
     */
    private void populateCustomersTable(){
        customerIdCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, Integer>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("phone"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("country"));
        customerStateProvinceCol.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("stateProvince"));
        customerTable.setItems(customersCache.getCache());
    }

    /**
     * Private helper method used in the controller's initialization method to populate all local cache model collections to reduce database
     * server calls and for quick ease of access
     */
    private void populateLocalCaches(){
        try {
            contactsCache = ContactsCache.getInstance();
            countryCache = CountryCache.getInstance();
            divisionLevelCache = DivisionLevelCache.getInstance();
            usersCache = UsersCache.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Local Caches populated!");
    }

    /**
     * Event handler/listener that fires from the Add button and directs to the AddAppointmentView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @FXML
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(
                ApplicationMain.class.getResource("AddAppointmentView.fxml")).load(), 600, 400));
    }

    /**
     * Event handler/listener that fires from the Modify button and directs to the EditAppointmentView screen assigning
     * the ModifyAppointmentController.selectedAppointment to the selected Appointment on the Appointment table or alerts
     * the user to make a valid selection.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @FXML
    public void onModifyAppointment(ActionEvent actionEvent) throws IOException {
        try{
            if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
                ModifyAppointmentController.selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(
                        ApplicationMain.class.getResource("EditAppointmentView.fxml")).load(), 600, 400));
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        "You have not selected a valid appointment to modify!");
                alert.setHeaderText("Invalid Selection");
                alert.show();
            }
        }catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    /**
     * Deletes the selected Appointment on the Appointment Table and updates the MySQL database accordingly. Alerts
     * the user to make a valid selection if nothing is selected.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    @FXML
    public void onDeleteAppointment(ActionEvent actionEvent) {
        try{
            if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
                alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Do you want to delete this appointment?");
                alert.setHeaderText("Delete");
                alert.showAndWait().ifPresent(
                        response -> {
                            if (response == ButtonType.OK) {
                                try {
                                    AppointmentQuery.delete(appointmentTable.getSelectionModel().getSelectedItem());
                                    AppointmentsCache.getInstance().getCache().remove(appointmentTable.getSelectionModel().getSelectedItem());
                                    tableLabelUpdater(appointmentTablePlaceholderLabel, "Appointment");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        "You have not selected a valid appointment to delete!");
                alert.setHeaderText("Invalid Selection");
                alert.show();
            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Private helper method that keeps the associated table's placeholder text accurate to the current context of the table
     * For use in table search query methods and table delete methods.
     * @param label Associated table's placeholder label
     * @param type String of associated object type: Part or Product
     */
    private void tableLabelUpdater(Label label, String type) throws SQLException {
        boolean isEmpty = false;
        switch (type) {
            case "Appointment" -> {
                isEmpty = AppointmentsCache.getInstance().getCache().isEmpty();
                if (isEmpty) {
                    label.setText("No " + type + "s Scheduled");
                } else {
                    label.setText(type + " Name/Id not found");
                }
            }
            case "Customer" -> {
                isEmpty = CustomersCache.getInstance().getCache().isEmpty();
                if (isEmpty) {
                    label.setText(type + " Table Empty");
                } else {
                    label.setText(type + " Name/Id not found");
                }
            }
            default -> {
                System.out.println("Unexpected type value for tableLabelUpdater");
            }
        }
    }

    /**
     * Event handler/listener that fires from the Add button and directs to the AddCustomerView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @FXML
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(
                ApplicationMain.class.getResource("AddCustomerView.fxml")).load(), 464, 400));
    }

    /**
     * Event handler/listener that fires from the Modify button and directs to the ModifyCustomerView screen assigning
     * the ModifyCustomerController.selectedCustomer to the selected Customer on the Customer table or alerts
     * the user to make a valid selection.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @FXML
    public void onModifyCustomer(ActionEvent actionEvent) {
        try{
            CustomerTable customer = customerTable.getSelectionModel().getSelectedItem();
            if(customer != null) {
                ModifyCustomerController.selectedCustomer = customer;
                ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(
                        ApplicationMain.class.getResource("ModifyCustomerView.fxml")).load(), 464, 400));
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        "You have not selected a valid customer to modify!");
                alert.setHeaderText("Invalid Selection");
                alert.show();
            }
        }catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    /**
     * Deletes the selected Customer on the Customer Table and updates the MySQL database accordingly. Alerts
     * the user to make a valid selection if nothing is selected.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    @FXML
    public void onDeleteCustomer(ActionEvent actionEvent) {
        try{
            CustomerTable customer = customerTable.getSelectionModel().getSelectedItem();
            if(customer != null) {
                if(hasAppointment(customer)){
                    alert = new Alert(Alert.AlertType.ERROR,
                            "Cannot delete customer "+customer.getCustomerName()+" (ID: "+customer.getCustomerId()+
                                    ") while customer's appointments exist!");
                    alert.setHeaderText("Invalid Selection");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete this Customer?");
                    alert.setHeaderText("Delete");
                    alert.showAndWait().ifPresent(
                            response -> {
                                if (response == ButtonType.OK) {
                                    try {
                                        CustomerQuery.delete(customer);
                                        CustomersCache.getInstance().getCache().remove(customer);
                                        tableLabelUpdater(customerTablePlaceholderLabel, "Customer");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        "You have not selected a valid customer to delete!");
                alert.setHeaderText("Invalid Selection");
                alert.show();
            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Private helper method used in onDeleteCustomer to check if selected Customer has any Appointments to prevent
     * invalid or redundant appointments and to emulate and comply with the Database Tables delete restrictions.
     * @param customer selected customer from the CustomerTable
     * @return boolean of if selected customer has an existing appointment
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private boolean hasAppointment(CustomerTable customer) throws SQLException {
        return AppointmentsCache.getInstance().getCache().stream()
                .anyMatch(x -> x.getCustomerId() == customer.getCustomerId());
    }

    /**
     * Event handler/listener that fires from the Reports button and directs to the ReportsView screen assigning
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @FXML
    public void onOpenReports(ActionEvent actionEvent) throws IOException {
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(
                ApplicationMain.class.getResource("ReportAlertView.fxml")).load(), 365, 180));
    }

    /**
     * Event handler/listener that fires from the Exit button and exits the program.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    @FXML
    public void onExit(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * Event handler/listener that fires from the Weekly radio button and filters the Appointment Table accordingly.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    public void onViewWeekRadioBtn(ActionEvent actionEvent) {
        populateAppointmentsTable();
    }

    /**
     * Event handler/listener that fires from the Monthly radio button and filters the Appointment Table accordingly.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    public void onViewMonthRadioBtn(ActionEvent actionEvent) {
        populateAppointmentsTable();
    }

    /**
     * Event handler/listener that fires from the All radio button and filters the Appointment Table accordingly.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    public void onViewAllRadioBtn(ActionEvent actionEvent) {
        populateAppointmentsTable();
    }
}
