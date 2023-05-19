package AppointmentScheduler;

import helper.JDBC_Source;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("", Locale.getDefault());
        JDBC_Source.openConnection();
        launch(args);
        //JDBC_Source.closeConnection();
    }
}