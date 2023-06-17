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
                //Maybe try filtering after the fact?
                //contactTable.setItems(AppointmentsCache.getInstance().getCache());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMainMenuBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onContactMenuBox(ActionEvent actionEvent) throws SQLException {
        Contact selectedContact = contactMenuBox.getSelectionModel().getSelectedItem();
        ObservableList<AppointmentTable> appointmentTables = contactTable.getItems();
        appointmentTables.filtered(x -> x.getContactId() == selectedContact.getContactId());
        populateTable();
    }
}
