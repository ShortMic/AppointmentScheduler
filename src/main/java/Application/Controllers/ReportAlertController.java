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
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The ReportAlertController which is responsible for the logic behind ReportAlertView. It generates the
 * filtered report data which populates the GUI table view for appointments associated with the selected contact.
 *
 * @author Michael Short
 * @version 1.0
 */
public class ReportAlertController implements Initializable{

    public Button generateBtn;
    public Button cancelBtn;
    public ComboBox<String> reportComboBox;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportComboBox.getItems().addAll("Month/Type", "Contact", "User");
        reportComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Event handler/listener that fires from the cancel button and redirects back to the MainMenuView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onCancelBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    /**
     * Event handler/listener that fires from the Generate button to route the application to the specified data report view
     * based on the user selection made in the Report Combo Box.
     */
    public void onGenerateBtn(ActionEvent actionEvent) throws IOException {
        String selectedOption = reportComboBox.getSelectionModel().getSelectedItem();
        switch(selectedOption){
            case "Month/Type":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("MonthTypeReportView.fxml")).load(), 313, 417));
                break;
            case "Contact":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("ContactTypeReportView.fxml")).load(), 740, 417));
                break;
            case "User":
                ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                        new FXMLLoader(ApplicationMain.class.getResource("UserTypeReportView.fxml")).load(), 740, 417));
                break;
            default:
                System.out.println("Something went wrong! No valid option selected for onOpenReports modal window!");
                break;
        }
    }
}
