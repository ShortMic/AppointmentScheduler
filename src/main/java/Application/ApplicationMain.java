package Application;

import Application.Models.User;
import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * An Appointment Scheduler application with a GUI that is tied to a backend MySQL database with local caching capabilities.
 * Keeps track of appointments and users and their respective schedules as well as clients, contacts, address and personal info.
 * This app also provides robust warnings, data validation and error handling as well reporting capabilities. The
 * application also has a login screen which helps protect and control access privileged client/business information
 * which is intended for internal use for users that have been assigned credentials.
 *
 * @author Michael Short
 * @version 1.0
 */
public class ApplicationMain extends Application {

    /**
     * The Application start method which houses the top level commands that sets and loads the main scene components,
     * controller and view for the Window stage display. Loads the initial login screen for application access.
     * @param stage represents a Stage object in which displays the window GUI and child scene objects
     * @throws IOException An exception for unintended input/output from/for the user when accepting and parsing text from the user
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The Application main method which launches the app. Javadoc method and class details/descriptions are located
     * in the root/javadoc folder.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        String exitMsg = "Closing system";
        if(User.isAssigned()){
            System.out.println(exitMsg+" and logging user "+ User.getCurrentUserName()+" out!");
            User.clear();
        }else{
            System.out.println(exitMsg+"...");
        }
        JDBC.closeConnection();
    }
}