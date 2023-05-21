package Application.Controllers;

import Application.ApplicationMain;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public static Locale locale;
    public static final boolean DEBUG = false;
    private ResourceBundle rb;
    private Alert alert;
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
        //Locale settings
        //https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=5251c286-743e-410d-b7c9-ab4901120742
        rb = resourceBundle;
        if(DEBUG){Locale.setDefault(new Locale("fr","FR"));}
        locale = Locale.getDefault();
        String localeCountry = locale.getCountry();
        String localeLanguage = locale.getLanguage();
        if(!locale.toString().equals("en_US")){
            System.out.println("Application language non-default mode.");
            rb = ResourceBundle.getBundle("Application/Translation_fr", locale);
            System.out.println(rb.getString("Hello"));
            loginLabel.setText(translate(loginLabel));
            appTitleLabel.setText(translate(appTitleLabel));
            passwordLabel.setText(translate(passwordLabel));
            userNameLabel.setText(translate(userNameLabel));
            localeLabel.setText(translate(localeLabel));
            submitCredentialsButton.setText(translate(submitCredentialsButton.getText()));
        }else{
            System.out.println("Application language default mode.");
        }
        if(localeCountry.isEmpty()){
            System.out.println("Locale unrecognized! Defaulting country to US");
        }else{
            zoneIdLabel.setText(locale.toString());
        }
    }

    private String translate(Text t){
        String[] str = t.getText().toString().split("\\s+");
        String translation = "";
        for(String s: str){
            try{
                translation += rb.getString(s)+" ";
            }catch(Exception e){
                System.out.println(e.getMessage());
                translation += s+" ";
            }
        }
        return translation;
    }

    private String translate(Label t){
        String[] str = t.getText().toString().split("\\s+");
        String translation = "";
        for(String s: str){
            try{
                translation += rb.getString(s)+" ";
            }catch(Exception e){
                System.out.println(e.getMessage());
                translation += s+" ";
            }
        }
        return translation;
    }

    private String translate(String t){
        String[] str = t.split("\\s+");
        String translation = "";
        for(String s: str){
            try{
                translation += rb.getString(s)+" ";
            }catch(Exception e){
                System.out.println(e.getMessage());
                translation += s+" ";
            }
        }
        return translation;
    }

    @FXML
    public void onLoginSubmit(ActionEvent actionEvent) throws SQLException {
//        System.out.println(actionEvent.getSource().toString()+": "+actionEvent.getEventType().getName().toString());
//        System.out.println(actionEvent.toString());
        int userId = 0;
        try{
            userId = UserQuery.select(userNameTextbox.getText(), passwordTextbox.getText());
            if(userId > 0){
                System.out.println("User and password accepted!");
            }else if(userId == -1){
                alert = new Alert(Alert.AlertType.ERROR,
                        translate("Invalid username and/or password!"));
                alert.setHeaderText(translate("Login failed"));
                alert.show();
            }else{
                alert = new Alert(Alert.AlertType.ERROR,
                        translate("Something went wrong with credential verification!"));
                alert.setHeaderText(translate("Login failed"));
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