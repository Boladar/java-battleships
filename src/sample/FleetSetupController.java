package sample;

        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.control.RadioButton;
        import javafx.scene.control.Toggle;
        import javafx.scene.control.ToggleGroup;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.GridPane;

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

    private ToggleGroup shipChoice;

    private ShipType currentlyPlacing = ShipType.DESTROYER;

    public FleetSetupController(BattleshipsController battleshipsController) {this.battleshipsController = battleshipsController;}

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

        shipChoice.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (shipChoice.getSelectedToggle() != null) {

                    System.out.println(shipChoice.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton

                }
            }
        });


        GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());
    }




    public void clickGrid(MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        double row = GridPane.getRowIndex(clickedNode);
        double columnIndex = GridPane.getColumnIndex(clickedNode);



        clickedNode.setStyle("-fx-background-color: " + "black");
        clickedNode.setStyle("");

    }
}
