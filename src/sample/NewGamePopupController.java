package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGamePopupController implements Initializable {

    private final BattleshipsController battleshipsController;

    @FXML
    private RadioButton singlePlayerRadio;
    @FXML
    private RadioButton hotSeatRadio;
    @FXML
    private ChoiceBox<Integer> gridSizeChoiceBox;
    @FXML
    private ColorPicker fleetColorPicker;
    @FXML
    private Button startGameButton;

    public NewGamePopupController(BattleshipsController battleshipsController) {this.battleshipsController = battleshipsController;}

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            ToggleGroup gameTypeGroup = new ToggleGroup();
            singlePlayerRadio.setToggleGroup(gameTypeGroup);
            hotSeatRadio.setToggleGroup(gameTypeGroup);

            gameTypeGroup.selectToggle(singlePlayerRadio);

            for(int i = 5; i <= 15;i++){
                gridSizeChoiceBox.getItems().add(i);
            }
            gridSizeChoiceBox.setValue(10);
    }

    public void startNewGame(){

    }
}
