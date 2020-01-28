package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGamePopupController implements Initializable {

    private final BattleshipsController battleshipsController;
    private  Boolean isSingleplayer;

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

    private ToggleGroup gameTypeGroup;

    public NewGamePopupController(BattleshipsController battleshipsController) {this.battleshipsController = battleshipsController;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameTypeGroup = new ToggleGroup();
        singlePlayerRadio.setToggleGroup(gameTypeGroup);
        hotSeatRadio.setToggleGroup(gameTypeGroup);

        gameTypeGroup.selectToggle(singlePlayerRadio);
        isSingleplayer = true;


        for (int i = 8; i <= 15; i++) {
            gridSizeChoiceBox.getItems()
                             .add(i);
        }
        gridSizeChoiceBox.setValue(10);

        startGameButton.setOnAction(actionEvent -> {
            try {

                if(gameTypeGroup.getSelectedToggle().equals(singlePlayerRadio)){

                    battleshipsController.setFirstPlayer( new Player(gridSizeChoiceBox.getValue(),new SinglePlayerTurnStrategy()));
                    battleshipsController.setSecondPlayer( new Player(gridSizeChoiceBox.getValue(),new AiTurnStrategy()));
                    isSingleplayer = true;

                }else{

                    battleshipsController.setFirstPlayer( new Player(gridSizeChoiceBox.getValue(),new HotSeatPlayerStrategy()));
                    battleshipsController.setSecondPlayer( new Player(gridSizeChoiceBox.getValue(),new HotSeatPlayerStrategy()));
                    isSingleplayer = false;

                }

                openFleetSetup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void openFleetSetup() throws IOException {
        ((Stage) startGameButton.getScene().getWindow()).close();
        Stage stage = new Stage();
        stage.setTitle("Fleet Setup");

        battleshipsController.setGRID_SIZE( gridSizeChoiceBox.getValue() );

        Color selectedColor = fleetColorPicker.getValue();

        battleshipsController.getFirstPlayer().setFleetColor(selectedColor);
        battleshipsController.getSecondPlayer().setFleetColor(selectedColor);

        Pane myPane = null;

        FXMLLoader loader = new FXMLLoader();
        FleetSetupController controller = new FleetSetupController(this.battleshipsController, isSingleplayer);//calling class controller
        loader.setController(controller);

        myPane = loader.load(getClass().getResource("fleetSetup.fxml").openStream());
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        stage.show();
    }
}
