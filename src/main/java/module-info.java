module AppointmentScheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Application to javafx.fxml;
    exports Application;
    exports Application.Controllers;
    opens Application.Controllers to javafx.fxml;
}