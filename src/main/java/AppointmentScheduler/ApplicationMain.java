package AppointmentScheduler;

import AppointmentScheduler.Models.User;
import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class ApplicationMain extends Application {

    public static Locale locale;

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
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("", Locale.getDefault());
        String test1 = "blah";
        String test2 = "blah bla";
        String testResult = String.format("this is formatted %1$s and %1$s and %2$s", test1, test2);
        System.out.println(testResult);
        locale = Locale.getDefault();
        if(!locale.toString().equals("en_US")){
            System.out.println("Application language non-default mode.");
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