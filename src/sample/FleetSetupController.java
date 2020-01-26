package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FleetSetupController implements Initializable {

    private final BattleshipsController battleshipsController;

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

    public FleetSetupController(BattleshipsController battleshipsController) {
        this.battleshipsController = battleshipsController;
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
            //currentPlayer =
        });
    }

    private void updateGrid(Player player){
        GridUtils.resetGridPane(fleetGridPane);

        for (Ship s : currentPlayer.getPlayerShips()){
            GridUtils.colorShip(s,"black",fleetGridPane);
        }

        GridUtils.colorShip(currentShip,"red",fleetGridPane);
    }

    public void clickGrid(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        int row = GridPane.getRowIndex(clickedNode);
        int columnIndex = GridPane.getColumnIndex(clickedNode);

        Tile t = currentPlayer.getMyGridTileByRowAndColumnt(row,columnIndex);


        if(currentShip.getClaimedTiles().contains(t))
            currentShip.getClaimedTiles().remove(t);
        else if(currentPlayer.checkIfTileIsSuitable(t,currentShip))
            currentShip.getClaimedTiles().add(t);


        updateGrid(currentPlayer);
    }
}
