package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.*;
import Application.Repository.CountryCache;
import Application.Repository.CustomersCache;
import Application.Repository.DivisionLevelCache;
import Utilities.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable{
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
    public static CustomerTable selectedCustomer;
    
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
        if(selectedCustomer != null){
            customerIDTextField.setText(Integer.toString(selectedCustomer.getCustomerId()));
            nameTextField.setText(selectedCustomer.getCustomerName());
            addressTextField.setText(selectedCustomer.getAddress());
            zipTextField.setText(selectedCustomer.getPostalCode());
            phoneTextField.setText(selectedCustomer.getPhone());
            try {
                countryMenuBtn.getSelectionModel().select(CountryCache.getInstance().getCache().stream().filter(x -> x.getCountry().equals(selectedCustomer.getCountry())).findFirst().orElse(null));
                countryMenuModifiedFlag = true;
                populateStateMenuBtn(countryMenuBtn.getSelectionModel().getSelectedItem().getCountryId());
                stateMenuBtn.getSelectionModel().select(DivisionLevelCache.getInstance().getCache().stream().filter(x -> x.getDivision().equals(selectedCustomer.getStateProvince())).findFirst().orElse(null));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCancelCustomerBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onModifyCustomerBtn(ActionEvent actionEvent) {
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
                    selectedCustomer.setCustomerName(nameTextField.getText());
                    selectedCustomer.setCountry(countryMenuBtn.getSelectionModel().getSelectedItem().getCountry());
                    selectedCustomer.setStateProvince(stateMenuBtn.getSelectionModel().getSelectedItem().getDivision());
                    selectedCustomer.setDivisionId(stateMenuBtn.getSelectionModel().getSelectedItem().getDivisionId());
                    selectedCustomer.setAddress(addressTextField.getText());
                    selectedCustomer.setPostalCode(zipTextField.getText());
                    selectedCustomer.setPhone(phoneTextField.getText());
                    CustomerQuery.update(selectedCustomer);
                    ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
                }
            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

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
