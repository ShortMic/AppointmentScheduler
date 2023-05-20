package Application;

import Application.Models.User;
import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationMain extends Application {

    public static Locale locale;
    public static final boolean DEBUG = true;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        //Locale settings
        //https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=5251c286-743e-410d-b7c9-ab4901120742
        if(DEBUG){Locale.setDefault(new Locale("fr"));}
        locale = Locale.getDefault();
        if(!locale.toString().equals("en_US")){
            System.out.println("Application language non-default mode.");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Application/Translation_fr", locale);
            System.out.println(resourceBundle.getString("Hello"));
        }else{
            System.out.println("Application language default mode.");
        }
        JDBC.openConnection();
        /*
        int rowsAffected = UserQuery.update(3,"Michael", "Alligator1");
        if(rowsAffected > 0){
            System.out.println("Insert successful!");
        }else{
            System.out.println("Insert failed!");
        }
        */
        launch(args);
        String exitMsg = "Closing system";
        if(User.isAssigned()){
            System.out.println(exitMsg+" and logging user "+User.getUserName()+" out!");
            User.clear();
        }else{
            System.out.println(exitMsg+"...");
        }
        JDBC.closeConnection();
    }
}