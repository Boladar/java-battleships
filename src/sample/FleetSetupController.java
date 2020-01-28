package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class FleetSetupController implements Initializable {

    private final BattleshipsController battleshipsController;
    private final Boolean isSingleplayer;

    @FXML
    private GridPane fleetGridPane;

    @FXML
    private RadioButton destroyerRadio;
    @FXML
    private RadioButton submarineRadio;
    @FXML
    private RadioButton cruiserRadio;
    @FXML
    private RadioButton battleshipRadio;
    @FXML
    private RadioButton carrierRadio;

    @FXML
    private Button doneButton;

    private ToggleGroup shipChoice;
    private Ship currentShip;

    private Player currentPlayer;


    public FleetSetupController(BattleshipsController battleshipsController, Boolean isSingleplayer) {
        this.battleshipsController = battleshipsController;
        this.isSingleplayer = isSingleplayer;
    }

    public void assignRadioButton(RadioButton button, ShipType type){
        button.setToggleGroup(shipChoice);
        button.setUserData(type);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shipChoice = new ToggleGroup();
        assignRadioButton(destroyerRadio,ShipType.DESTROYER);
        assignRadioButton(submarineRadio,ShipType.SUBMARINE);
        assignRadioButton(cruiserRadio,ShipType.CRUISER);
        assignRadioButton(battleshipRadio,ShipType.BATTLESHIP);
        assignRadioButton(carrierRadio,ShipType.CARRIER);
        shipChoice.selectToggle(destroyerRadio);

        currentPlayer = battleshipsController.getFirstPlayer();
        currentShip = currentPlayer.getPlayerShipByType((ShipType) shipChoice.getSelectedToggle().getUserData());

        shipChoice.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (shipChoice.getSelectedToggle() != null) {
                GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());
                currentShip = currentPlayer.getPlayerShipByType((ShipType) shipChoice.getSelectedToggle().getUserData());
                updateGrid(currentPlayer);
            }
        });

        GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());

        doneButton.setOnAction(actionEvent -> {
            if(!isSingleplayer){
                if( currentPlayer.equals(battleshipsController.getSecondPlayer())){
                    Stage stage = (Stage)doneButton.getScene().getWindow();
                    stage.close();
                    battleshipsController.startGame();
                }

                currentPlayer = battleshipsController.getSecondPlayer();

                GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());
                GridUtils.resetGridPane(fleetGridPane);
                updateGrid(currentPlayer);
            }
            else{
            generateFleetPositions(battleshipsController.getSecondPlayer());

            Stage stage = (Stage)doneButton.getScene().getWindow();
            stage.close();
            battleshipsController.startGame();
            }
        });
    }

    private Boolean checkTile(Player target,int row, int column){
        Tile t = target.getMyGridTileByRowAndColumn(row,column);
        return !t.isOccupied();
    }

    private boolean checkVertical(Player target, int column, int startRow, int endRow ){
        for (int i = startRow; i <= endRow; i++) {
            if(!checkTile(target,i,column))
                return false;
        }

        return true;
    }

    private void addVertical(Player target,Ship ship,int column , int startRow , int endRow){
        for (int i = startRow; i <= endRow; i++) {
            Tile t = target.getMyGridTileByRowAndColumn(i,column);

            t.setOccupied(true);
            ship.getClaimedTiles().add(t);
        }

    }

    private boolean checkHorizontal(Player target, int row , int startColumn , int endColumn){
        for (int i = startColumn; i <= endColumn; i++) {
            if(!checkTile(target,row,i))
                return false;
        }

        return true;
    }

    private void addHorizontal(Player target,Ship ship,int row , int startColumn , int endColumn){
        for (int i = startColumn; i <= endColumn; i++) {
            Tile t = target.getMyGridTileByRowAndColumn(row,i);

            t.setOccupied(true);
            ship.getClaimedTiles().add(t);
        }

    }

    private void generateFleetPositions(Player target){

        List<Ship> ships = target.getPlayerShips();
        ships.sort(Comparator.comparingInt(Ship::getSize).reversed());

        Random generator = new Random();

        for(Ship s : ships){

            boolean isPositionRight = false;

            while(!isPositionRight) {
                int startRow = generator.nextInt(battleshipsController.getGRID_SIZE() - 1 - s.getSize()) + 1;
                int startColumn = generator.nextInt(battleshipsController.getGRID_SIZE() - 1 - s.getSize()) + 1;

                boolean vertical = generator.nextBoolean();

                int endRow = startRow;
                int endColumn = startColumn;

                if (vertical) {
                    endRow = startRow + s.getSize() -1 ;
                    if(checkVertical(target,endColumn,startRow,endRow)) {
                        addVertical(target, s, endColumn, startRow, endRow);
                        isPositionRight = true;
                    }
                } else {
                    endColumn = startColumn + s.getSize() - 1;
                    if(checkHorizontal(target,endRow,startColumn,endColumn)) {
                        addHorizontal(target, s, endRow, startColumn, endColumn);
                        isPositionRight = true;
                    }
                }
            }
        }

    }

    private void updateGrid(Player player){
        GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());
        GridUtils.resetGridPane(fleetGridPane);


        for (Ship s : currentPlayer.getPlayerShips()){
            GridUtils.colorShip(s,player.getFleetColor(),fleetGridPane);
        }

        GridUtils.colorShip(currentShip,Color.RED,fleetGridPane);
    }

    public void clickGrid(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        int row = GridPane.getRowIndex(clickedNode);
        int columnIndex = GridPane.getColumnIndex(clickedNode);

        Tile t = currentPlayer.getMyGridTileByRowAndColumn(row,columnIndex);


        if(currentShip.getClaimedTiles().contains(t))
            currentShip.getClaimedTiles().remove(t);
        else if(currentPlayer.checkIfTileIsSuitable(t,currentShip))
            currentShip.getClaimedTiles().add(t);


        updateGrid(currentPlayer);
    }


}
