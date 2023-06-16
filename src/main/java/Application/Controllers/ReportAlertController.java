package Application.Controllers;

import Application.ApplicationMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportAlertController implements Initializable{
    /*
    Each of the following reports and will display the reports in the user interface:
        •  the total number of customer appointments by type and month
        •  a schedule for each contact in your organization that includes appointment ID, title, type and description,
            start date and time, end date and time, and customer ID
        •  an additional report of your choice that is different from the two other required reports in this prompt and
            from the user log-in date and time stamp that will be tracked in part C
     */
    @FXML
    public Text appTitleLabel;
    public Button generateBtn;
    public Button cancelBtn;
    public ComboBox<String> reportComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportComboBox.getItems().addAll("Month/Type", "Contact", "User");
        reportComboBox.getSelectionModel().selectFirst();
    }

    public void onCancelBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onGenerateBtn(ActionEvent actionEvent) throws IOException {
        String selectedOption = reportComboBox.getSelectionModel().getSelectedItem();
        switch(selectedOption){
            case "Month/Type":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("MonthTypeReportView.fxml")).load(), 313, 417));
                break;
            case "Contact":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("ContactTypeReportView.fxml")).load(), 1070, 564));
                break;
            case "User":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("UserReportView.fxml")).load(), 1070, 564));
                break;
            default:
                System.out.println("Something went wrong! No valid option selected for onOpenReports modal window!");
                break;
        }
    }
}
