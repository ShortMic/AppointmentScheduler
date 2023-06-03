package Application.Controllers;

import Application.ApplicationMain;
import Application.Repository.ContactsCache;
import Application.Repository.CustomersCache;
import Application.Repository.UsersCache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable{

    @FXML
    public Text appTitleLabel;
    public Alert alert;
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
    public MenuButton userIdMenuBtn;
    EventHandler<ActionEvent> menuSelection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuSelection = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ((MenuButton)actionEvent.getSource()).setText(((MenuItem)actionEvent.getSource()).getText());
            }
        };
        userIdMenuBtn.getItems().clear();
        startTimeMenuBtn.getItems().clear();
        endTimeMenuBtn.getItems().clear();
        customerIDMenuBtn.getItems().clear();
        contactMenuBtn.getItems().clear();
        try {
            UsersCache.getInstance().getCache().forEach((x) -> userIdMenuBtn.getItems().add(new MenuItem(Integer.toString(x.getUserId()))));
            //userIdMenuBtn.setContentDisplay();
            userIdMenuBtn.setOnAction(menuSelection);
            CustomersCache.getInstance().getCache().forEach((x) -> customerIDMenuBtn.getItems().add(new MenuItem(Integer.toString(x.getCustomerId()))));
            ContactsCache.getInstance().getCache().forEach((x) -> contactMenuBtn.getItems().add(new MenuItem(x.getContactName())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateTimeSelection(startTimeMenuBtn);
        populateTimeSelection(endTimeMenuBtn);
    }

    private void populateTimeSelection(MenuButton menu){
        for(int i = 1; i < 13; i++){
            menu.getItems().add(new MenuItem(Integer.toString(i)+" AM"));
        }
        for(int i = 1; i < 13; i++){
            menu.getItems().add(new MenuItem(Integer.toString(i)+" PM"));
        }
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

        try{
            textFieldDataValidationLogger("Title", titleTextField, "String");
            textFieldDataValidationLogger("Description", descriptionTextField, "String");
            textFieldDataValidationLogger("Type", typeTextField, "String");
            textFieldDataValidationLogger("Location", locationTextField, "String");
//
//            if(amount >= min && amount <= max && min >= 0){
//                if (outSourceRadioButton.isSelected()){
//                    newPart = new Outsourced(IMSApplication.uniqueId++, AddPartNameTextField.getText(),
//                            Double.parseDouble(AddPartCostTextField.getText()), amount, min, max,
//                            AddPartMachineIDTextField.getText());
//                }else{
//                    newPart = new InHouse(IMSApplication.uniqueId++, AddPartNameTextField.getText(),
//                            Double.parseDouble(AddPartCostTextField.getText()), amount, min, max,
//                            Integer.parseInt(AddPartMachineIDTextField.getText()));
//                }
//                IMSApplication.inventory.addPart(newPart);
//                ((Stage)(((Button)e.getSource()).getScene().getWindow())).setScene(mainScene);
//            }else{
//                alert = new Alert(Alert.AlertType.ERROR,
//                        "Minimum must be equal to or below maximum and inventory amount must be within range!");
//                alert.setHeaderText("Invalid Input");
//                alert.show();
//            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
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

    public void onUserIdMenuBtn(ActionEvent actionEvent) {

    }
}
