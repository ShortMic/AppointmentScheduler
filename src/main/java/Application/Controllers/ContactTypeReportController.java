package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.AppointmentTable;
import Application.Models.Contact;
import Application.Repository.AppointmentsCache;
import Application.Repository.ContactsCache;
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

/**
 * The ContactTypeReportController which is responsible for the logic behind ContactTypeReportView. It generates the
 * filtered report data which populates the GUI table view for appointments associated with the selected contact.
 *
 * @author Michael Short
 * @version 1.0
 */
public class ContactTypeReportController implements Initializable {
    public Button mainMenuBtn;
    public ComboBox<Contact> contactMenuBox;
    public TableView<AppointmentTable> contactTable;
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
            ObservableList<Contact> contacts = FXCollections.observableList(ContactsCache.getInstance().getCache());
            contactMenuBox.setItems(contacts);
            contactMenuBox.setConverter(new StringConverter<Contact>() {
                @Override
                public String toString(Contact contact) {
                    return contact == null ? null : contact.getContactName();
                }

                @Override
                public Contact fromString(String s) {
                    return contacts.stream().filter(x -> x.getContactName().equals(s)).findFirst().orElse(null);
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
        if(!contactMenuBox.getSelectionModel().isEmpty()){
            Contact selectedContact = contactMenuBox.getSelectionModel().getSelectedItem();
            if(selectedContact == null){
                contactMenuBox.getSelectionModel().selectFirst();
                selectedContact = contactMenuBox.getSelectionModel().getSelectedItem();
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
                //Filter cached appointments with selectedContact
                Contact finalSelectedContact = selectedContact;
                ObservableList<AppointmentTable> appointmentTables = FXCollections.observableList(
                        AppointmentsCache.getInstance().getCache().stream()
                                .filter(x -> x.getContactId() == finalSelectedContact.getContactId()).toList());
                contactTable.setItems(appointmentTables);
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
    public void onContactMenuBox(ActionEvent actionEvent) throws SQLException {
        populateTable();
    }
}
