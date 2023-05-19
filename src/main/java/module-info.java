module AppointmentScheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens AppointmentScheduler to javafx.fxml;
    exports AppointmentScheduler;
    exports AppointmentScheduler.Controllers;
    opens AppointmentScheduler.Controllers to javafx.fxml;
}