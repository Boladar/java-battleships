package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Label turnLabel;

    @FXML
    private Label timerLabel;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(0);
    private Timeline timeline;

    @FXML
    private Button endTurnButton;

    Player firstPlayer;
    Player secondPlayer;

    Player currentPlayer;
    Player otherPlayer;

    private int movesLeft = 1;

    private int GRID_SIZE = 10;
    private int TILE_SIZE = 500;

    private boolean gameStarted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GridUtils.populateGrid(playerBoard,TILE_SIZE,GRID_SIZE);
        GridUtils.populateGrid(enemyBoard,TILE_SIZE,GRID_SIZE);
    }

    public void clickGrid(MouseEvent event){
        if(gameStarted) {

            Node clickedNode = event.getPickResult()
                                    .getIntersectedNode();
            int row = GridPane.getRowIndex(clickedNode);
            int columnIndex = GridPane.getColumnIndex(clickedNode);

            Tile t = currentPlayer.getEnemyGridByTileByRowAndColumn(row, columnIndex);
            Tile enemyTile = otherPlayer.getMyGridTileByRowAndColumn(row, columnIndex);

            if (!t.isHit() && movesLeft > 0) {
                t.setHit(true);
                enemyTile.setHit(true);
                movesLeft -= 1;
                currentPlayer.setMovements(currentPlayer.getMovements() + 1);
            }

            drawBoards();
        }
    }

    public void startGame(){
        gameStarted = true;

        timerLabel.textProperty().bind(timeSeconds.asString());
        handleTimer();

        startPlayerTurn(firstPlayer,secondPlayer);
    }

    public void showWinGame(String message){
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            WinPopupController controller = new WinPopupController(message);
            loader.setController(controller);
            parent = loader.load(getClass().getResource("winPopup.fxml").openStream());
            Scene scene = new Scene(parent);
            Stage appStage = (Stage) endTurnButton.getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextTurn(ActionEvent event){

        if(firstPlayer.getPlayerHealth() == 0)
            showWinGame("PLAYER 2 WINS");
        else if( secondPlayer.getPlayerHealth() == 0)
            showWinGame("PLAYER 1 WINS");
        else
        currentPlayer.getNewTurnStrategy().newTurn(this);
    }

    public void startPlayerTurn(Player currentPlayer, Player otherPlayer){
        if(currentPlayer.getMovements() == otherPlayer.getMovements()){
            turnLabel.setText(Integer.toString(currentPlayer.getMovements()));
        }

        movesLeft = 1;

        this.currentPlayer = currentPlayer;
        this.otherPlayer = otherPlayer;
        drawBoards();

    }

    public void resetBoards(){
        GridUtils.populateGrid(playerBoard,TILE_SIZE,GRID_SIZE);
        GridUtils.populateGrid(enemyBoard,TILE_SIZE,GRID_SIZE);
        GridUtils.resetGridPane(playerBoard);
        GridUtils.resetGridPane(enemyBoard);
    }

    public void drawBoards(){

        resetBoards();

        for (Ship s : currentPlayer.getPlayerShips()){
            GridUtils.colorShip(s,"black",playerBoard);
        }

        GridUtils.PaintHitMarks(playerBoard,enemyBoard,currentPlayer,otherPlayer,TILE_SIZE);
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

    public void setGRID_SIZE(int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
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

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }
}
