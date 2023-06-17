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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable{

    @FXML
    public Text appTitleLabel;
    public Alert alert;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField typeTextField;
    public TextField locationTextField;
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
    public TextField appointmentIdTextField;
    private ObservableList<String> timeSelectionList;
    private String errorMsg = "";
    private boolean errorFlag = false;

    public static AppointmentTable selectedAppointment;
    private DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter timeFormat24hr = DateTimeFormatter.ofPattern("h:mma");
    private LocalDateTime start;
    private LocalDateTime end;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(selectedAppointment != null) {
            titleTextField.setText(selectedAppointment.getTitle());
            descriptionTextField.setText(selectedAppointment.getDescription());
            typeTextField.setText(selectedAppointment.getType());
            locationTextField.setText(selectedAppointment.getLocation());
            locationTextField.setText(selectedAppointment.getLocation());

            appointmentIdTextField.setText(Integer.toString(selectedAppointment.getAppointmentId()));
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
            String startTimeStr = (selectedAppointment.getStart().toLocalTime().format(timeFormat24hr));
            startTimeMenuBtn.getSelectionModel().select(startTimeStr);
            String endTimeStr = (selectedAppointment.getEnd().toLocalTime().format(timeFormat24hr));
            endTimeMenuBtn.getSelectionModel().select(endTimeStr);
            userIdMenuBtn.getSelectionModel().select(selectedAppointment.getUserId());
            startDateField.setValue(selectedAppointment.getStart().toLocalDate());
            endDateField.setValue(selectedAppointment.getEnd().toLocalDate());
            try {
                userIdMenuBtn.getSelectionModel().select(UsersCache.getInstance().getCache().stream()
                        .filter(x -> x.getUserId() == selectedAppointment.getUserId()).findFirst().orElse(null));
                contactMenuBtn.getSelectionModel().select(ContactsCache.getInstance().getCache().stream()
                        .filter(x -> x.getContactId() == selectedAppointment.getContactId()).findFirst().orElse(null));
                customerIDMenuBtn.getSelectionModel().select(CustomersCache.getInstance().getCache().stream()
                        .filter(x -> x.getCustomerId() == selectedAppointment.getCustomerId()).findFirst().orElse(null));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR,
                    "No Appointment was selected! Loading main menu...");
            alert.setHeaderText("Invalid Selection");
            alert.showAndWait().ifPresent(
                    response -> {
                        if (response == ButtonType.OK) {
                            try {
                                ((Stage)(alert.getDialogPane().getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
    }

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

    public void onCancelAppointmentBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onModifyAppointmentBtn(ActionEvent actionEvent) throws IOException {
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
                    }else if(AppointmentsCache.getInstance().timeSlotConflict(start, end, Integer.parseInt(appointmentIdTextField.getText()))){
                        alert = new Alert(Alert.AlertType.ERROR, "Appointment slot conflicts with pre-existing appointment!");
                        alert.setHeaderText("Invalid Date/Time");
                        alert.show();
                    }else{
                        int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
                        AppointmentTable appointment = AppointmentsCache.getInstance().getCache().stream().filter(x -> x.getAppointmentId() == appointmentId).findFirst().orElse(null);
                        appointment.setTitle(titleTextField.getText());
                        appointment.setDescription(descriptionTextField.getText());
                        appointment.setType(typeTextField.getText());
                        appointment.setLocation(locationTextField.getText());
                        appointment.setContactId(contactMenuBtn.getSelectionModel().getSelectedItem().getContactId());
                        appointment.setContactName(contactMenuBtn.getSelectionModel().getSelectedItem().getContactName());
                        appointment.setStart(start);
                        appointment.setEnd(end);
                        appointment.setUserId(userIdMenuBtn.getSelectionModel().getSelectedItem().getUserId());
                        appointment.setCustomerId(customerIDMenuBtn.getSelectionModel().getSelectedItem().getCustomerId());
                        AppointmentQuery.update(appointment);
                        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(
                                new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
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
