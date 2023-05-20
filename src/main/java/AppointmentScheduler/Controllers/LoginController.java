package AppointmentScheduler.Controllers;

import AppointmentScheduler.ApplicationMain;
import Utilities.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Alert alert;
    private String localeCountry;
    private String localeLanguage;
    @FXML
    public TextField userNameTextbox;
    @FXML
    public Text userNameLabel;
    @FXML
    public TextField passwordTextbox;
    @FXML
    public Text passwordLabel;
    @FXML
    public Text appTitleLabel;
    @FXML
    public Text loginLabel;
    @FXML
    public Button submitCredentialsButton;
    @FXML
    public Label localeLabel;
    @FXML
    public Label zoneIdLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localeCountry = ApplicationMain.locale.getCountry();
        localeLanguage = ApplicationMain.locale.getLanguage();
        if(localeCountry.isEmpty()){
            System.out.println("Locale unrecognized! Defaulting country to US");
        }else{
            zoneIdLabel.setText(ApplicationMain.locale.toString());
        }
    }

    @FXML
    public void onLoginSubmit(ActionEvent actionEvent) throws SQLException {
        System.out.println(actionEvent.getSource().toString()+": "+actionEvent.getEventType().getName().toString());
        System.out.println(actionEvent.toString());
        int userId = 0;
        try{
            userId = UserQuery.select(userNameTextbox.getText(), passwordTextbox.getText());
            if(userId > 0){
                System.out.println("User and password accepted!");
            }else if(userId == -1){
                alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid username and/or password!");
                alert.setHeaderText("Login failed");
                alert.show();
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        "Something went wrong with credential verification!");
                alert.setHeaderText("Login failed");
                alert.show();
                System.out.println("Unexpected user id match outcome from Users table!");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

}