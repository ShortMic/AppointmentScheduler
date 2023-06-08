package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.*;
import Application.Repository.*;
import Utilities.AppointmentQuery;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable{

    @FXML
    public Text appTitleLabel;
    @FXML
    public Tab appointmentTab;
    @FXML public TableView<AppointmentTable> appointmentTable; //TODO: Make wrapper class for Appointment & Contact classes and utilize here
    @FXML public TableColumn<AppointmentTable, Integer> appointmentIdCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentTitleCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentDescriptionCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentLocationCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentContactCol;
    @FXML public TableColumn<AppointmentTable, String> appointmentTypeCol;
    @FXML public TableColumn<AppointmentTable, LocalDateTime> appointmentStartCol; //import sql.date or java.date? currently sql.date
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
    private Alert alert;

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
    }

    private void populateAppointmentsTable(){
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
        appointmentTable.setItems(appointmentsCache.getCache());
    }

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

    @FXML
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("AddAppointmentView.fxml")).load(), 600, 400));
    }

    @FXML
    public void onModifyAppointment(ActionEvent actionEvent) {
    }

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
            case "Customers" -> {
                isEmpty = CustomersCache.getInstance().getCache().isEmpty();
                if (isEmpty) {
                    label.setText(type + "s Table Empty");
                } else {
                    label.setText(type + " Name/Id not found");
                }
            }
            default -> {
                System.out.println("Unexpected type value for tableLabelUpdater");
            }
        }
    }

    @FXML
    public void onAddCustomer(ActionEvent actionEvent) {
    }

    @FXML
    public void onModifyCustomer(ActionEvent actionEvent) {
    }

    @FXML
    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    @FXML
    public void onOpenReports(ActionEvent actionEvent) {
    }

    @FXML
    public void onExit(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
