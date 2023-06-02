package Application.Controllers;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Application.Models.Contact;
import Application.Models.CustomerTable;
import Application.Repository.AppointmentsCache;
import Application.Repository.CustomersCache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
    @FXML public TableColumn<AppointmentTable, Date> appointmentStartCol; //import sql.date or java.date? currently sql.date
    @FXML public TableColumn<AppointmentTable, Date> appointmentEndCol;
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
    }

    private void populateAppointmentsTable(){
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Date>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Date>("end"));
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

    @FXML
    public void onAddAppointment(ActionEvent actionEvent) {
    }

    @FXML
    public void onModifyAppointment(ActionEvent actionEvent) {
    }

    @FXML
    public void onDeleteAppointment(ActionEvent actionEvent) {
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
