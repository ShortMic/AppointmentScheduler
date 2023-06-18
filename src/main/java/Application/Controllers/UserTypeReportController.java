package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.AppointmentTable;
import Application.Models.User;
import Application.Repository.AppointmentsCache;
import Application.Repository.UsersCache;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UserTypeReportController implements Initializable {
    public Button mainMenuBtn;
    public ComboBox<User> userMenuBox;
    public TableView<AppointmentTable> userTable;
    public TableColumn<AppointmentTable, Integer> appointmentIdCol;
    public TableColumn<AppointmentTable, String> titleCol;
    public TableColumn<AppointmentTable, String> typeCol;
    public TableColumn<AppointmentTable, String> descriptionCol;
    public TableColumn<AppointmentTable, LocalDateTime> startCol;
    public TableColumn<AppointmentTable, LocalDateTime> endCol;
    public TableColumn<AppointmentTable, Integer> customerIdCol;
    private boolean tableInitialized = false;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<User> users = FXCollections.observableList(UsersCache.getInstance().getCache());
            userMenuBox.setItems(users);
            userMenuBox.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User user) {
                    return user == null ? null : user.getUserName();
                }

                @Override
                public User fromString(String s) {
                    return users.stream().filter(x -> x.getUserName().equals(s)).findFirst().orElse(null);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A private helper method to populate the table with the filtered custom report data based on the user selection made
     * in the Contact Combo Box.
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    private void populateTable() throws SQLException {
        if(!userMenuBox.getSelectionModel().isEmpty()){
            User selectedUser = userMenuBox.getSelectionModel().getSelectedItem();
            if(selectedUser == null){
                userMenuBox.getSelectionModel().selectFirst();
                selectedUser = userMenuBox.getSelectionModel().getSelectedItem();
            }
            if(!tableInitialized){
                appointmentIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("appointmentId"));
                titleCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("description"));
                typeCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, String>("type"));
                startCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, LocalDateTime>("start"));
                endCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, LocalDateTime>("end"));
                customerIdCol.setCellValueFactory(new PropertyValueFactory<AppointmentTable, Integer>("customerId"));
                tableInitialized = true;
            }
            try {
                //Filter cached appointments with selectedUser
                User finalSelectedUser = selectedUser;
                ObservableList<AppointmentTable> appointmentTables = FXCollections.observableList(
                        AppointmentsCache.getInstance().getCache().stream()
                                .filter(x -> x.getUserId() == finalSelectedUser.getUserId()).toList());
                userTable.setItems(appointmentTables);
                //Maybe try filtering after the fact?
                //userTable.setItems(AppointmentsCache.getInstance().getCache());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Event handler/listener that fires from the main menu button and redirects back to the MainMenuView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onMainMenuBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    /**
     * Event handler/listener that fires from the Contact Combo Box and provides a filter for the data displayed on the
     * table based on the selection.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
    public void onUserMenuBox(ActionEvent actionEvent) throws SQLException {
        populateTable();
    }
}
