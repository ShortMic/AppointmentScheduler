module AppointmentScheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Application to javafx.fxml;
    exports Application;
    exports Application.Controllers;
    exports Application.Models;
    exports Application.Repository;
    opens Application.Controllers to javafx.fxml;
}