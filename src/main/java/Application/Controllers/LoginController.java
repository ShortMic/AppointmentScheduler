package Application.Controllers;

import Application.ApplicationMain;
import Utilities.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.FileWriter;
import java.io.PrintWriter;

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

    public void createLoginLog(String message) {
        String logFileName = "login_activity.txt";
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(logFileName, true))) {
            printWriter.println(message);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void loadMainMenu(ActionEvent e) throws IOException {
        MainMenuController.isInitialLogin = true;
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    @FXML
    public void onLoginSubmit(ActionEvent actionEvent) throws SQLException {
        int userId = 0;
        try{
            userId = UserQuery.select(userNameTextbox.getText(), passwordTextbox.getText());
            if(userId > 0){
                createLoginLog("["+LocalDateTime.now().toString()+"] User \""+userNameTextbox.getText()+"\" logged in successfully.");
                System.out.println("User and password accepted!");
                loadMainMenu(actionEvent);
            }else if(userId == -1){
                createLoginLog(LocalDateTime.now().toString()+": User \""+userNameTextbox.getText()+"\" login failed (credential mismatch).");
                alert = new Alert(Alert.AlertType.ERROR,
                        translate("Invalid username and/or password!"));
                alert.setHeaderText(translate("Login failed"));
                alert.show();
            }else{
                createLoginLog(LocalDateTime.now().toString()+": User \""+userNameTextbox.getText()+"\" login failed (connection error).");
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

}