package Application.Controllers;

import Application.ApplicationMain;
import Utilities.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable{

    @FXML
    public Text appTitleLabel;
    @FXML
    public Tab appointmentTab;
    @FXML
    public TableView appointmentTable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    }
}
