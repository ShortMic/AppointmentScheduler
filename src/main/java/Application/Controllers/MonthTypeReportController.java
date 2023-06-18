package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.Appointment;
import Application.Models.AppointmentTable;
import Application.Repository.AppointmentsCache;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * The MonthTypeReportController which is responsible for the logic behind ContactTypeReportView. It generates the
 * filtered report data which populates the GUI table view for appointments associated with the selected contact.
 * Provides the sum of all appointments per month or per type.
 *
 * @author Michael Short
 * @version 1.0
 */
public class MonthTypeReportController implements Initializable {

    @FXML
    public Button mainMenuBtn;
    public TableView<Month> monthTable;
    public TableColumn<Month, String> monthCol;
    public TableColumn<Month, Integer> monthQtyCol;
    public TableView<Map.Entry<String, Integer>> typeTable;
    public TableColumn<Map.Entry<String, Integer>, String> typeCol;
    public TableColumn<Map.Entry<String, Integer>, Integer> typeQtyCol;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Month> months = FXCollections.observableArrayList(new ArrayList<Month>(Arrays.stream(Month.values()).toList()));
        monthCol.setCellValueFactory(x -> {
            String monthName = x.getValue().getDisplayName(TextStyle.FULL, Locale.getDefault());
            return new SimpleStringProperty(monthName);
        });
        monthQtyCol.setCellValueFactory(x -> {
            int n = 0;
            try {
                n = (int) AppointmentsCache.getInstance().getCache().stream().filter(a -> a.getMonth().equals(x.getValue())).count();
                return new SimpleIntegerProperty(n).asObject();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return new SimpleIntegerProperty(n).asObject();
        });
        monthTable.setItems(months);

        HashMap<String, Integer> appointmentTypes = new HashMap<>();
        //Convert Appointment Types to HashMap entries that store their occurrence quantity
        try {
            AppointmentsCache.getInstance().getCache().forEach(x -> {
                appointmentTypes.put(x.getType(), appointmentTypes.getOrDefault(x.getType(), 0)+1);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        typeCol.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getKey()));
        typeQtyCol.setCellValueFactory(x -> new SimpleObjectProperty<Integer>(x.getValue().getValue()));
        //Convert HashMap entries to ObservableList
        ObservableList<Map.Entry<String, Integer>> filteredAppointmentTypes = FXCollections.observableArrayList(appointmentTypes.entrySet());
        typeTable.setItems(filteredAppointmentTypes);
    }

    /**
     * Event handler/listener that fires from the main menu button and redirects back to the MainMenuView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onMainMenuBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }
}
