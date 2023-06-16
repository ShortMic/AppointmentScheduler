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

    /*
    Each of the following reports and will display the reports in the user interface:
        •  a schedule for each user in your organization that includes appointment ID, title, type and description,
            start date and time, end date and time, and customer ID
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

    public void onMainMenuBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onUserMenuBox(ActionEvent actionEvent) throws SQLException {
        User selectedUser = userMenuBox.getSelectionModel().getSelectedItem();
        ObservableList<AppointmentTable> appointmentTables = userTable.getItems();
        appointmentTables.filtered(x -> x.getUserId() == selectedUser.getUserId());
        populateTable();
    }
}
