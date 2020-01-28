package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinScreenController implements Initializable {

    @FXML
    private Label winLabel;

    @FXML
    private Button closeButton;

    private final String text;

    public WinScreenController(String text) {this.text = text;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        winLabel.setText(text);
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
