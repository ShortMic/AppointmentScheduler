package Application.Controllers;

import Application.ApplicationMain;
import Application.Models.Country;
import Application.Models.DivisionLevel1;
import Application.Repository.CountryCache;
import Application.Repository.DivisionLevelCache;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable{
    //TODO: Implement AddCustomerController
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

    public void onStartTimeMenuBtn(ActionEvent actionEvent) {
    }

    public void onEndTimeMenuBtn(ActionEvent actionEvent) {
    }

    public void onStartDateField(ActionEvent actionEvent) {
    }

    public void onEndDateField(ActionEvent actionEvent) {
    }

    public void onCancelCustomerBtn(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    public void onAddCustomerBtn(ActionEvent actionEvent) {

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
