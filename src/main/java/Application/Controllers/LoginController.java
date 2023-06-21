package Application.Controllers;

import Application.ApplicationMain;
import Utilities.TimeConverter;
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

/**
 * The LoginController providing logic behind LoginView with error handling and login credential verification that helps
 * control application access to intended users.
 *
 * @author Michael Short
 * @version 1.0
 */
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

    /**
     * The initialize method (inherited by Initializable) which runs automatically upon loading the controller's
     * associated fxml file in the main application class. Initializes pre-declared table view components and links associated
     * Part collections with their respective field properties to column categories.
     * @param url loads the url (automatically inherited and handled by the javafx framework)
     * @param resourceBundle loads the associated resourceBundle (automatically inherited and handled by the javafx framework)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rb = resourceBundle;
        if(DEBUG){Locale.setDefault(new Locale("fr","FR"));}
        zoneIdLabel.setText(TimeConverter.getUserTimeZone().toString());
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
        }
    }

    /**
     * Creates (or appends the pre-existing) login_activity.txt logging file at the application's root.
     * @param message Custom detailed log message passed in
     */
    public void createLoginLog(String message) {
        String logFileName = "login_activity.txt";
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(logFileName, true))) {
            printWriter.println(message);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private helper method that translates the passed in argument to the current local machine language resource bundle
     * (provided the locale language is supported).
     * @param t The text to translate
     * @return Translated String
     */
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

    /**
     * Private helper method that translates the passed in argument to the current local machine language resource bundle
     * (provided the locale language is supported).
     * @param t The Label to translate
     * @return Translated String
     */
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

    /**
     * Private helper method that translates the passed in argument to the current local machine language resource bundle
     * (provided the locale language is supported).
     * @param t The String to translate
     * @return Translated String
     */
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

    /**
     * Private helper method that loads the main menu and sets the isInitialLogin flag to true for the initial login upcoming
     * appointment alert window prompt
     * @param e Pre-generated and auto-handled event argument.
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    private void loadMainMenu(ActionEvent e) throws IOException {
        MainMenuController.isInitialLogin = true;
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).setScene(new Scene(new FXMLLoader(ApplicationMain.class.getResource("MainMenuView.fxml")).load(), 1070, 564));
    }

    /**
     * The Login Controller's credential validation method. Provides error handling and verifies user credential with
     * the User Database and grants or denies access to the application accordingly.
     * @param actionEvent Pre-generated and auto-handled event argument.
     * @throws SQLException An exception for unexpected SQL issues (i.e. connectivity problems, query syntax errors, data type errors, etc)
     */
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