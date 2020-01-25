package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class BattleshipsController implements Initializable {

    @FXML
    private GridPane playerBoard;

    private final int GRID_HEIGHT = 500;
    private final int GRID_WIDTH = 500;

    private final int GRID_SIZE = 10;
    private final int TILE_SIZE = 50;


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

        for(int i =0; i <= GRID_SIZE;i++){
            for(int j = 0; j <= GRID_SIZE;j++){
                if( i == 0 && j != 0){
                    target.add(createFieldLabel(Integer.toString(j)),i,j);
                }
                else if (i != 0 && j == 0){
                    target.add(createFieldLabel(String.valueOf((char)(i - 1 + 'A'))),i,j);
                }
                else{
                    target.add(new ShipPart(TILE_SIZE,TILE_SIZE,Color.WHITE),i,j);
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hello world");

        playerBoard.setMaxHeight(GRID_HEIGHT);
        playerBoard.setMaxWidth(GRID_WIDTH);

        populateGrid(playerBoard);

    }

    public void clickGrid(MouseEvent event){
        Node clickedNode = event.getPickResult().getIntersectedNode();

        System.out.println(clickedNode);

        //playerBoard.add(new ShipPart());

    }

}
