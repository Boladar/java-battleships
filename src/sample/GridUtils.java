package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Objects;

public class GridUtils {

    private static Label createFieldLabel(String text, double tileSize){
        Label label = new Label();

        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setFont(Font.font("Courier-new",20));
        label.setText(text);
        label.setPrefSize(tileSize,tileSize);

        return label;
    }

    public static void populateGrid(GridPane target, double tileSize, double gridSize){

        target.setMaxSize(tileSize*gridSize,tileSize*gridSize);
        target.setPrefSize(tileSize,tileSize);

        for(int i =0; i <= gridSize;i++){
            for(int j = 0; j <= gridSize;j++){
                if( i == 0 && j != 0){
                    target.add(createFieldLabel(Integer.toString(j), tileSize),i,j);
                }
                else if (i != 0 && j == 0){
                    target.add(createFieldLabel(String.valueOf((char)(i - 1 + 'A')), tileSize),i,j);
                }
                else{
                    Pane pane = new Pane();
                    target.add(pane,i,j);
                }
            }
        }
    }

    public static void colorShip(Ship ship, String color, GridPane gridPane){

        List<Tile> tiles = ship.getClaimedTiles();

        for(Tile t : tiles){
            colorNode(Objects.requireNonNull(getNodeByRowColumnIndex(t.getRow(), t.getColumn(), gridPane)),color);
        }

    }

    public static void resetGridPane(GridPane pane){
        for(Node node : pane.getChildren()){
            node.setStyle("");
        }
    }

    public static void colorNode(Node target, String color){
        if(target != null){
            target.setStyle("-fx-background-color: " + color + ";");
        }
    }

    public static void drawGridPane(Tile[][] data, GridPane target){

        for (int i = 0; i < target.getColumnCount();i++){
            for(int j = 0; j < target.getRowCount();j++){
                Tile currnet = data[i][j];

            }
        }

    }

    public static Node getNodeByRowColumnIndex (Integer row,Integer column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if(row == rowIndex && column == columnIndex)
                return node;
        }

        return null;
    }
}
