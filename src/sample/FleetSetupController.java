package sample;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.GridPane;

        import java.net.URL;
        import java.util.ResourceBundle;

public class FleetSetupController implements Initializable {

    private final BattleshipsController battleshipsController;

    @FXML
    private GridPane fleetGridPane;

    public FleetSetupController(BattleshipsController battleshipsController) {this.battleshipsController = battleshipsController;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("welcome to fleet setup");

        GridUtils.populateGrid(fleetGridPane,battleshipsController.getTILE_SIZE(),battleshipsController.getGRID_SIZE());
    }
}
