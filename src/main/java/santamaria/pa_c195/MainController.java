package santamaria.pa_c195;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button button1;
    public Label label1;

    @FXML
    protected void onButtonClick() {
        label1.setText("Welcome!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }
}