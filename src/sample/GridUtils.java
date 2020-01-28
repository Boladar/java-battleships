package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
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

    private static Label getBaseLabel(double tileSize){
        Label label = new Label();

        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setPrefSize(tileSize,tileSize);

        return label;
    }

    private static Label createHitIndicator(double tileSize, Color color){
        Label label = getBaseLabel(tileSize);

        label.setFont(Font.font("Courier-new",20));
        label.setTextFill(color);
        label.setText("X");

        return label;
    }

    private static Label createFieldLabel(String text, double tileSize){
        Label label = getBaseLabel(tileSize);

        label.setFont(Font.font("Courier-new",20));
        label.setText(text);

        return label;
    }

    public static void populateGrid(GridPane target, double tileSize, double gridSize){

        Node node = target.getChildren().get(0);
        target.getChildren().clear();
        target.getChildren().add(0,node);

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

    private static void paintHitShips(GridPane targetGrid,Player targetPlayer,int tileSize){
        for(Ship s : targetPlayer.getPlayerShips()) {
            for (Tile t : s.getClaimedTiles()) {
                if (t.isHit()) {
                    targetGrid.add(createHitIndicator(tileSize, Color.RED), t.getColumn(), t.getRow());
                }
            }
        }
    }

    private static void paintHitTiles(GridPane playerGrid, GridPane enemyGrid, Player targetPlayer,int tileSize){

        for(int i = 0; i < targetPlayer.getGridSize();i++){
            for(int j = 0; j < targetPlayer.getGridSize();j++){
                Tile t = targetPlayer.getMyGridTileByRowAndColumn(i,j);

                if(t.isHit()) {
                    playerGrid.add(createHitIndicator(tileSize, Color.BLACK), t.getColumn(), t.getRow());
                }

                Tile enemyT = targetPlayer.getEnemyGridByTileByRowAndColumn(i,j);
                if(enemyT.isHit()){
                    enemyGrid.add(createHitIndicator(tileSize, Color.BLACK),enemyT.getColumn(),enemyT.getRow());
                }
            }

        }

    }

    public static void PaintHitMarks(GridPane playerGrid, GridPane enemyGrid, Player targetPlayer, Player otherPlayer, int tileSize){
        paintHitTiles(playerGrid,enemyGrid,targetPlayer,tileSize);
        paintHitShips(playerGrid,targetPlayer,tileSize);
        paintHitShips(enemyGrid,otherPlayer,tileSize);
    }

    public static void colorShip(Ship ship, Color color, GridPane gridPane){

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

    public static void colorNode(Node target, Color color){

        double r = color.getRed() * 255;
        double g = color.getGreen() * 255;
        double b = color.getBlue() * 255;



        if(target != null){
            target.setStyle("-fx-background-color: " + "rgb(" + r + "," + g  +"," + b + ")" + ";");
        }
    }

    public static Node getNodeByRowColumnIndex (Integer row,Integer column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if(row.equals(rowIndex) && column.equals(columnIndex))
                return node;
        }

        return null;
    }
}
