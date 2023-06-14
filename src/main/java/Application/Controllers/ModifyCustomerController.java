package Application.Controllers;

import Application.Models.CustomerTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable{
//TODO: Implement ModifyCustomerController

    public static CustomerTable selectedCustomer;

    @FXML
    public Text appTitleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
    }
}
