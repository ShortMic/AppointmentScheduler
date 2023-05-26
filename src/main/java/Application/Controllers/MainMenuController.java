package Application.Controllers;

import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Application.Models.Contact;
import Application.Repository.AppointmentsCache;
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
    @FXML
    public TableView<AppointmentTable> appointmentTable; //TODO: Make wrapper class for Appointment & Contact classes and utilize here
    public TableColumn<AppointmentTable, Integer> appointmentIdCol;
    public TableColumn<AppointmentTable, String> appointmentTitleCol;
    public TableColumn<AppointmentTable, String> appointmentDescriptionCol;
    public TableColumn<AppointmentTable, String> appointmentLocationCol;
    public TableColumn<AppointmentTable, String> appointmentContactCol;
    public TableColumn<AppointmentTable, String> appointmentTypeCol;
    public TableColumn<AppointmentTable, Date> appointmentStartCol; //import sql.date or java.date? currently sql.date
    public TableColumn<AppointmentTable, Date> appointmentEndCol;
    public TableColumn<AppointmentTable, Integer> appointmentCustomerIdCol;
    public TableColumn<AppointmentTable, Integer> appointmentUserIdCol;
    @FXML
    public Button addAppointmentBtn;
    @FXML
    public Button modifyAppointmentBtn;
    @FXML
    public Button deleteAppointmentBtn;
    @FXML
    public TableView<AppointmentTable> customerTable;
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
    @FXML
    public AppointmentsCache appointmentsCache;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsCache = new AppointmentsCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        appointmentTable.setItems(appointmentsCache.getAppointments());
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
