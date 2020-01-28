package sample;

import java.util.Random;

public class AiTurnStrategy implements NewTurnStrategy {


    @Override
    public void newTurn(BattleshipsController battleshipsController, Player nextCurrent, Player other) {

        Random generator = new Random();
        boolean goodChoice = false;

        while(!goodChoice) {

            int column = generator.nextInt(nextCurrent.getGridSize() - 1) + 1;
            int row = generator.nextInt(nextCurrent.getGridSize() -1) + 1;

            Tile t = nextCurrent.getEnemyGridByTileByRowAndColumn(row,column);

            if(!t.isHit()){
                t.setHit(true);
                Tile enemyTile = other.getMyGridTileByRowAndColumn(row,column);
                enemyTile.setHit(true);

                goodChoice = true;
            }
        }

        battleshipsController.startPlayerTurn(other,nextCurrent);
    }
}
