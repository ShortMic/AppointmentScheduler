package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.*;
import Application.Repository.AppointmentsCache;
import Application.Repository.ContactsCache;
import Application.Repository.CustomersCache;
import Application.Repository.UsersCache;
import Utilities.AppointmentQuery;
import Utilities.TimeConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The AddAppointmentController which is responsible for the logic behind AddAppointmentView. It helps provide I/O
 * functionality and acts as an interface between the front end GUI and backend database and provides user data validation,
 * error handling and local cache storage for creating new Appointment POJOs.
 *
 * @author Michael Short
 * @version 1.0
 */
public class AddAppointmentController implements Initializable{

    @FXML
    public Alert alert;
    public TextField locationTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField typeTextField;
    public ArrayList<ComboBox> comboFields;
    public ComboBox<String> startTimeMenuBtn;
    public ComboBox<String> endTimeMenuBtn;
    public ComboBox<User> userIdMenuBtn;
    public ComboBox<Contact> contactMenuBtn;
    public ComboBox<CustomerTable> customerIDMenuBtn;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public Button cancelAppointmentBtn;
    public Button addAppointmentBtn;
    private ObservableList<String> timeSelectionList;
    private String errorMsg = "";
    private boolean errorFlag = false;

    private DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter timeFormat24hr = DateTimeFormatter.ofPattern("h:mma");
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboFields = new ArrayList<>();
        comboFields.add(startTimeMenuBtn);
        comboFields.add(endTimeMenuBtn);
        comboFields.add(userIdMenuBtn);
        comboFields.add(contactMenuBtn);
        comboFields.add(customerIDMenuBtn);
        try {
            userIdMenuBtn.setItems(UsersCache.getInstance().getCache());
            userIdMenuBtn.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User user) {
                    return user == null ? null : Integer.toString(user.getUserId());
                }

                @Override
                public User fromString(String s) {
                    try {
                        return UsersCache.getInstance().getCache().stream().filter(x -> Integer.toString(x.getUserId()).equals(s)).findFirst().orElse(null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            contactMenuBtn.setItems(ContactsCache.getInstance().getCache());
            contactMenuBtn.setConverter(new StringConverter<Contact>() {
                @Override
                public String toString(Contact contact) {
                    return contact == null ? null : contact.getContactName();
                }

                @Override
                public Contact fromString(String s) {
                    try {
                        return ContactsCache.getInstance().getCache().stream().filter(x -> x.getContactName().equals(s)).findFirst().orElse(null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            customerIDMenuBtn.setItems(CustomersCache.getInstance().getCache());
            customerIDMenuBtn.setConverter(new StringConverter<CustomerTable>() {
                @Override
                public String toString(CustomerTable customerTable) {
                    return customerTable == null ? null : Integer.toString(customerTable.getCustomerId());
                }

                @Override
                public CustomerTable fromString(String s) {
                    try {
                        return CustomersCache.getInstance().getCache().stream()
                                .filter(x -> Integer.toString(x.getCustomerId()).equals(s))
                                .findFirst()
                                .orElse(null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateTimeSelection();
        startTimeMenuBtn.setItems(timeSelectionList);
        endTimeMenuBtn.setItems(timeSelectionList);
    }

    /**
     * A private helper method meant to populate the startTimeMenuBtn and endTimeMenuBtn with a time range that complies
     * with business hours that has automatically been converted from EST to the user's local machine time.
     */
    private void populateTimeSelection(){
        timeSelectionList = FXCollections.observableArrayList();
        for(int i = 0; i < TimeConverter.getHoursOpen(); i++){
            timeSelectionList.add(LocalTime.of((TimeConverter.getOffsetHour(TimeConverter.getBusinessOpeningTime()
                    .getHour())+i)%24, 0, 0).format(timeFormat24hr).toString());
            timeSelectionList.add(LocalTime.of((TimeConverter.getOffsetHour(TimeConverter.getBusinessOpeningTime()
                    .getHour())+i)%24, 15, 0).format(timeFormat24hr).toString());
            timeSelectionList.add(LocalTime.of((TimeConverter.getOffsetHour(TimeConverter.getBusinessOpeningTime()
                    .getHour())+i)%24, 30, 0).format(timeFormat24hr).toString());
            timeSelectionList.add(LocalTime.of((TimeConverter.getOffsetHour(TimeConverter.getBusinessOpeningTime()
                    .getHour())+i)%24, 45, 0).format(timeFormat24hr).toString());
        }
        timeSelectionList.add(LocalTime.of(TimeConverter.getOffsetHour(TimeConverter.getBusinessClosingTime()
                .getHour()), 0, 0).format(timeFormat24hr).toString());
        ZoneId userZone = TimeConverter.getUserTimeZone();
        System.out.println(userZone.toString());
    }

    /**
     * Event handler/listener that fires from the cancel button and redirects back to the MainMenuView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onCancelAppointmentBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    /**
     * Event handler/listener that fires from the add Appointment button and attempts to create and add the appointment
     * and it's field details to the local cache and database.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onAddAppointmentBtn(ActionEvent actionEvent) throws IOException {
        try{
            textFieldDataValidationLogger("Title", titleTextField, "String");
            textFieldDataValidationLogger("Description", descriptionTextField, "String");
            textFieldDataValidationLogger("Type", typeTextField, "String");
            textFieldDataValidationLogger("Location", locationTextField, "String");
            if(errorFlag){
                alert = new Alert(Alert.AlertType.ERROR, errorMsg);
                alert.setHeaderText("Invalid Input");
                alert.show();
                errorMsg = "";
                errorFlag = false;
            }else{
                errorFlag = comboFields.stream().anyMatch(comboBox -> comboBox.getSelectionModel().getSelectedItem() == null);
                if(errorFlag){
                    alert = new Alert(Alert.AlertType.ERROR, "Combo Box Field(s) not entered. Please make a selection!");
                    alert.setHeaderText("Missing Required Field");
                    alert.show();
                    errorFlag = false;
                }else{
                    LocalTime localStartTime = LocalTime.parse(startTimeMenuBtn.getSelectionModel().getSelectedItem(), timeFormat24hr);
                    LocalTime localEndTime = LocalTime.parse(endTimeMenuBtn.getSelectionModel().getSelectedItem(), timeFormat24hr);
                    start = LocalDateTime.of(startDateField.getValue(), localStartTime);
                    end = LocalDateTime.of(endDateField.getValue(), localEndTime);
                    if(start.isAfter(end)){
                        alert = new Alert(Alert.AlertType.ERROR, "Start date/time is after end date/time!");
                        alert.setHeaderText("Invalid Date/Time");
                        alert.show();
                    }else if(AppointmentsCache.getInstance().timeSlotConflict(start, end)){
                        alert = new Alert(Alert.AlertType.ERROR, "Appointment slot conflicts with pre-existing appointment!");
                        alert.setHeaderText("Invalid Date/Time");
                        alert.show();
                    }else if(!TimeConverter.duringBusinessHours(start, end)){
                        alert = new Alert(Alert.AlertType.ERROR, "Appointment slot is not during business hours!");
                        alert.setHeaderText("Invalid Date/Time");
                        alert.show();
                    }else{
                        //temporary id to replace on insert update query
                        Appointment appointment = new Appointment(-1, titleTextField.getText(), descriptionTextField.getText(),
                                locationTextField.getText(), typeTextField.getText(), start, end,
                                customerIDMenuBtn.getSelectionModel().getSelectedItem().getCustomerId(), userIdMenuBtn.getSelectionModel().getSelectedItem().getUserId(), contactMenuBtn.getSelectionModel().getSelectedItem().getContactId());
                        appointment.setAppointmentId(AppointmentQuery.create(appointment));
                        AppointmentsCache.getInstance().getCache().add(new AppointmentTable(appointment, contactMenuBtn.getSelectionModel().getSelectedItem().getContactName()));
                        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
                    }
                }
            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Private helper method that logs conversion error messages for the associated text fields and their expected types
     * @param textFieldLabel    The currently asserted text field label
     * @param textField         The currently asserted text field object
     * @param expectedType      The expected datatype to assert for
     */
    private void textFieldDataValidationLogger(String textFieldLabel, TextField textField, String expectedType){
        String message = "";
        switch(expectedType){
            case "int":
                if(!tryParseInt(textField.getText())){
                    message = "Invalid type for "+textFieldLabel+" field: "+"\""+textField.getText()+"\" is not an "+expectedType;
                }
                break;
            case "Double":
                if(!tryParseDouble(textField.getText())){
                    message = "Invalid type for "+textFieldLabel+": "+"\""+textField.getText()+"\" is not a "+expectedType;
                }
                break;
            case "String":
                if(textField.getText().isEmpty()){
                    message = "Required field "+textFieldLabel+" is empty!";
                }
            default:
                break;
        }
        if(!message.equals("")){
            errorMsg += message+" ";
            errorFlag = true;
            System.out.println(message);
        }
    }

    /**
     * Private helper method which returns whether a string is convertible to an int.
     * @param text  The text to try converting into an int
     * @return boolean
     */
    private boolean tryParseInt(String text){
        try{
            Integer.parseInt(text);
        }catch(Exception exception){
            return false;
        }
        return true;
    }

    /**
     * Private helper method which returns whether a string is convertible to a double.
     * @param text  The text to try converting into a double
     * @return boolean
     */
    private boolean tryParseDouble(String text){
        try{
            Double.parseDouble(text);
        }catch(Exception exception){
            return false;
        }
        return true;
    }
}
