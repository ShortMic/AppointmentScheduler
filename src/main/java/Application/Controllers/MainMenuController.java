package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.Appointment;
import Application.Models.Contact;
import Application.Repository.AppointmentsCache;
import Utilities.JDBC;
import Utilities.UserQuery;
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
    public TableView appointmentTable;
    public TableColumn<Appointment, Integer> appointmentId;
    public TableColumn<Appointment, String> appointmentTitle;
    public TableColumn<Appointment, String> appointmentDescription;
    public TableColumn<Appointment, String> appointmentLocation;
    public TableColumn<Contact, String> appointmentContact;
    public TableColumn<Appointment, String> appointmentType;
    public TableColumn<Appointment, Date> appointmentStart; //import sql.date or java.date? currently sql.date
    public TableColumn<Appointment, Date> appointmentEnd;
    public TableColumn<Appointment, Integer> appointmentCustomerId;
    public TableColumn<Appointment, Integer> appointmentUserId;
    @FXML
    public Button addAppointmentBtn;
    @FXML
    public Button modifyAppointmentBtn;
    @FXML
    public Button deleteAppointmentBtn;
    @FXML
    public TableView customerTable;
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
        appointmentId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<Contact, String>("contactName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("end"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentUserId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
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
