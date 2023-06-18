package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.*;
import Application.Repository.AppointmentsCache;
import Application.Repository.CountryCache;
import Application.Repository.CustomersCache;
import Application.Repository.DivisionLevelCache;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The AddCustomerController which is responsible for the logic behind AddCustomerView. It helps provide I/O
 * functionality and acts as an interface between the front end GUI and backend database and provides user data validation,
 * error handling and local cache storage for creating new Customer POJOs.
 *
 * @author Michael Short
 * @version 1.0
 */
public class AddCustomerController implements Initializable{
    @FXML
    public TextField customerIDTextField;
    public Label stateLabel;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipTextField;
    public TextField phoneTextField;
    public ComboBox<Country> countryMenuBtn;
    public ComboBox<DivisionLevel1> stateMenuBtn;
    public Button addCustomerBtn;
    public Button cancelCustomerBtn;
    private boolean countryMenuModifiedFlag = false;
    private String errorMsg = "";
    private boolean errorFlag = false;
    public Alert alert;

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     *
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            countryMenuBtn.setItems(CountryCache.getInstance().getCache());
            countryMenuBtn.setConverter(new StringConverter<Country>() {
                @Override
                public String toString(Country country) {
                    return country == null ? null : country.getCountry();
                }

                @Override
                public Country fromString(String s) {
                    try {
                        return CountryCache.getInstance().getCache().stream().filter(x -> x.getCountry().equals(s)).findFirst().orElse(null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Event handler/listener that fires from the cancel button and redirects back to the MainMenuView screen.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    public void onCancelCustomerBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    /**
     * Event handler/listener that fires from the add Customer button and attempts to create and add the Customer
     * and their field details to the local cache and database.
     * @param actionEvent Pre-generated and auto-handled event argument.
     */
    public void onAddCustomerBtn(ActionEvent actionEvent) {
        try{
            textFieldDataValidationLogger("Name", nameTextField, "String");
            textFieldDataValidationLogger("Address", addressTextField, "String");
            textFieldDataValidationLogger("Zipcode", zipTextField, "String");
            textFieldDataValidationLogger("Phone", phoneTextField, "String");
            if(errorFlag){
                alert = new Alert(Alert.AlertType.ERROR, errorMsg);
                alert.setHeaderText("Invalid Input");
                alert.show();
                errorMsg = "";
                errorFlag = false;
            }else{
                errorFlag = (countryMenuBtn.getSelectionModel().getSelectedItem() == null || stateMenuBtn.getSelectionModel().getSelectedItem() == null);
                if(errorFlag){
                    alert = new Alert(Alert.AlertType.ERROR, "Combo Box Field(s) not entered. Please make a selection!");
                    alert.setHeaderText("Missing Required Field");
                    alert.show();
                    errorFlag = false;
                }else{
                    Customer customer = new Customer(-1, nameTextField.getText(), addressTextField.getText(),
                            zipTextField.getText(), phoneTextField.getText(), stateMenuBtn.getSelectionModel().getSelectedItem().getDivisionId());
                    customer.setCustomerId(CustomerQuery.create(customer));
                    CustomersCache.getInstance().getCache().add(new CustomerTable(customer, countryMenuBtn.getSelectionModel().getSelectedItem().getCountry(), stateMenuBtn.getSelectionModel().getSelectedItem().getDivision()));
                    ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
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

    /**
     * Event handler/listener that fires from the user selecting a country option in the Combo Box. Doing this enables
     * the state/province Combo Box to be selectable and populates its choices according to the user's country selection.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws SQLException
     */
    public void onSelectCountryBtn(ActionEvent actionEvent) throws SQLException {
        Country selectedCountry = ((ComboBox<Country>)actionEvent.getSource()).getSelectionModel().getSelectedItem();
        if(selectedCountry != null){
            stateMenuBtn.setDisable(false);
            populateStateMenuBtn(selectedCountry.getCountryId());
            countryMenuModifiedFlag = true;
        }else if(countryMenuModifiedFlag){
            stateMenuBtn.getSelectionModel().clearSelection();
        }
    }

    /**
     * Private helper method which populates the state/province Combo Box based on the countryId provided.
     * @param countryId The country id from the selected country object.
     * @throws SQLException
     */
    private void populateStateMenuBtn(int countryId) throws SQLException {
        List<DivisionLevel1> filteredCountryStates = DivisionLevelCache.getInstance().getCache().stream().filter(x -> x.getCountryId() == countryId).toList();
        ObservableList<DivisionLevel1> countryStates = FXCollections.observableArrayList(new ArrayList<DivisionLevel1>(filteredCountryStates));
        stateMenuBtn.setItems(countryStates);
        stateMenuBtn.setConverter(new StringConverter<DivisionLevel1>() {
            @Override
            public String toString(DivisionLevel1 divisionLevel1) {
                return divisionLevel1 == null ? null : divisionLevel1.getDivision();
            }

            @Override
            public DivisionLevel1 fromString(String s) {
                try {
                    return DivisionLevelCache.getInstance().getCache().stream().filter(x -> x.getDivision().equals(s)).findFirst().orElse(null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
