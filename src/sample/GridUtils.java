package sample;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

}
