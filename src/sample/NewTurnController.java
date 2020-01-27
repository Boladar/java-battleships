package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTurnController implements Initializable {

    private final BattleshipsController battleshipsController;

    public NewTurnController(BattleshipsController battleshipsController) {this.battleshipsController = battleshipsController;}

    @FXML
    private Label playerLabel;

    @FXML
    private Button newTurnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(battleshipsController.otherPlayer.equals(battleshipsController.getFirstPlayer())){
            playerLabel.setText("PLAYER 1");
        }
        else{
            playerLabel.setText("PLAYER 2");
        }

        newTurnButton.setOnAction(actionEvent -> {

            battleshipsController.startPlayerTurn(battleshipsController.getOtherPlayer(),battleshipsController.getCurrentPlayer());
            ((Stage) newTurnButton.getScene().getWindow()).close();
        });

    }
}
