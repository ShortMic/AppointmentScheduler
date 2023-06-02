package Application.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable{

    @FXML
    public Text appTitleLabel;
    public TextField locationTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField typeTextField;
    public MenuButton startTimeMenuBtn;
    public MenuButton contactMenuBtn;
    public MenuButton customerIDMenuBtn;
    public MenuButton endTimeMenuBtn;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public Button cancelAppointmentBtn;
    public Button addAppointmentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onStartTimeMenuBtn(ActionEvent actionEvent) {
    }

    public void onEndTimeMenuBtn(ActionEvent actionEvent) {
    }

    public void onStartDateField(ActionEvent actionEvent) {
    }

    public void onEndDateField(ActionEvent actionEvent) {
    }

    public void onCancelAppointmentBtn(ActionEvent actionEvent) {

    }

    public void onAddAppointmentBtn(ActionEvent actionEvent) {
    }
}
