package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotSeatPlayerStrategy implements  NewTurnStrategy{
    @Override
    public void newTurn(BattleshipsController battleshipsController) {

        battleshipsController.resetBoards();

        Stage defStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        NewTurnController controller = new NewTurnController(battleshipsController);//calling class controller
        loader.setController(controller);

        Parent frame = null;
        try {
            frame = loader.load(getClass().getResource("newTurn.fxml").openStream());

            Scene sceneDef = new Scene(frame);

            defStage.setTitle("New Game Settings");
            defStage.setScene(sceneDef);
            defStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
