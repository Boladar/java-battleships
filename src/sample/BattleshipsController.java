package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    @FXML
    private GridPane playerBoard;

    @FXML
    private GridPane enemyBoard;

    @FXML
    private Label timerLabel;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(0);
    private Timeline timeline;

    Player firstPlayer;
    Player secondPlayer;

    private int GRID_SIZE = 10;
    private int TILE_SIZE = 500;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timerLabel.textProperty().bind(timeSeconds.asString());
        handleTimer();

        GridUtils.populateGrid(playerBoard,TILE_SIZE,GRID_SIZE);
        GridUtils.populateGrid(enemyBoard,TILE_SIZE,GRID_SIZE);

        firstPlayer = new Player(GRID_SIZE);
        secondPlayer = new Player(GRID_SIZE);
    }

    public void clickGrid(MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        System.out.println(clickedNode);

        //playerBoard.add(new ShipPart());

    }

    public void showNewGamePopup() throws IOException {
        Stage defStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        NewGamePopupController controller = new NewGamePopupController(this);//calling class controller
        loader.setController(controller);
        Parent frame = loader.load(getClass().getResource("newGamePopup.fxml").openStream());
        Scene sceneDef = new Scene(frame);

        defStage.setTitle("New Game Settings");
        defStage.setScene(sceneDef);
        defStage.show();
    }

    private void updateTime() {
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds + 1);
    }

    private void handleTimer(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),evt -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeSeconds.set(0);
        timeline.play();
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
