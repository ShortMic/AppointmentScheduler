package Application.Controllers;

import Application.ApplicationMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable{

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

    public void onCancelAppointmentBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onAddAppointmentBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

}
