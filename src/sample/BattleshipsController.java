package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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

    private final int GRID_SIZE = 10;
    private final int TILE_SIZE = 500;

    private OnClickController onClickController;

    private Label createFieldLabel(String text){
        Label label = new Label();

        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setFont(Font.font("Courier-new",20));
        label.setText(text);
        label.setPrefSize(TILE_SIZE,TILE_SIZE);

        return label;
    }

    private void populateGrid(GridPane target){

        target.setMaxSize(TILE_SIZE*GRID_SIZE,TILE_SIZE*GRID_SIZE);
        target.setPrefSize(TILE_SIZE,TILE_SIZE);

        for(int i =0; i <= GRID_SIZE;i++){
            for(int j = 0; j <= GRID_SIZE;j++){
                if( i == 0 && j != 0){
                    target.add(createFieldLabel(Integer.toString(j)),i,j);
                }
                else if (i != 0 && j == 0){
                    target.add(createFieldLabel(String.valueOf((char)(i - 1 + 'A'))),i,j);
                }
                else{
                    Pane pane = new Pane();
                    target.add(pane,i,j);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timerLabel.textProperty().bind(timeSeconds.asString());
        handleTimer();

        populateGrid(playerBoard);
        populateGrid(enemyBoard);
    }

    public void clickGrid(MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        System.out.println(clickedNode);

        //playerBoard.add(new ShipPart());

    }

    public void newGame() throws IOException {
        Stage defStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        NewGamePopupController controller = new NewGamePopupController(this);//calling class controller
        loader.setController(controller);
        Parent frame = loader.load(getClass().getResource("newGamePopup.fxml").openStream());
        Scene sceneDef = new Scene(frame);

        defStage.setTitle("New Game");
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

}
