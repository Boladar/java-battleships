package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
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

        for (int i = 5; i <= 15; i++) {
            gridSizeChoiceBox.getItems()
                             .add(i);
        }
        gridSizeChoiceBox.setValue(10);

        startGameButton.setOnAction(actionEvent -> {
            try {
                startNewGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void closeStage()
    {
        ((Stage) startGameButton.getScene().getWindow()).close();
    }

    public void startNewGame() throws IOException {
        closeStage();
        Stage stage = new Stage();
        stage.setTitle("Fleet Setup");
        Pane myPane = null;

        FXMLLoader loader = new FXMLLoader();
        FleetSetupController controller = new FleetSetupController(this.battleshipsController);//calling class controller
        loader.setController(controller);

        myPane = loader.load(getClass().getResource("fleetSetup.fxml").openStream());
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        stage.show();
    }
}
