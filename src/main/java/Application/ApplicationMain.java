package Application;

import Application.Models.User;
import Utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 480);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
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
            System.out.println(exitMsg+" and logging user "+ User.getCurrentUserName()+" out!");
            User.clear();
        }else{
            System.out.println(exitMsg+"...");
        }
        JDBC.closeConnection();
    }
}