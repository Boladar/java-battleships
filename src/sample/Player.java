package sample;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int gridSize;
    private Tile[][] myGrid;
    private Tile[][] enemyGrid;
    private List<Ship> playerShips;

    private int movements = 0;

    private final NewTurnStrategy newTurnStrategy;

    private void createTileGrid(Tile[][] target) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                target[i][j] = new Tile(i, j);
            }
        }
    }

    public Player(int gridSize, NewTurnStrategy newTurnStrategy) {
        this.gridSize = gridSize + 1;

        myGrid = new Tile[gridSize + 1][gridSize + 1];
        enemyGrid = new Tile[gridSize + 1][gridSize + 1];
        this.newTurnStrategy = newTurnStrategy;

        createTileGrid(myGrid);
        createTileGrid(enemyGrid);

        this.playerShips = new ArrayList<>();
        playerShips.add(new Ship(ShipType.DESTROYER));
        playerShips.add(new Ship(ShipType.SUBMARINE));
        playerShips.add(new Ship(ShipType.CRUISER));
        playerShips.add(new Ship(ShipType.BATTLESHIP));
        playerShips.add(new Ship(ShipType.CARRIER));
    }

    public int getPlayerHealth(){

        int result = 0;

        for(Ship s : playerShips){
            result += s.getHealth();
        }

        return  result;
    }

    public Boolean checkIfTileIsSuitable(Tile tile, Ship ship) {

        for (Ship s : playerShips) {
            if (s.getClaimedTiles()
                 .contains(tile))
                return false;
        }

        return ship.checkIfTileIsSuitable(tile);
    }


    public Ship getPlayerShipByType(ShipType type) {

        for (Ship ship : playerShips) {
            if (ship.getType()
                    .equals(type))
                return ship;
        }
        return null;
    }

    public Tile getMyGridTileByRowAndColumn(int row, int column) {
        return myGrid[row][column];
    }

    public Tile getEnemyGridByTileByRowAndColumn(int row, int column) {
        return enemyGrid[row][column];
    }

    public Tile[][] getMyGrid() {
        return myGrid;
    }

    public Tile[][] getEnemyGrid() {
        return enemyGrid;
    }

    public List<Ship> getPlayerShips() {
        return playerShips;
    }

    public int getGridSize() {
        return gridSize;
    }

    public NewTurnStrategy getNewTurnStrategy() {
        return newTurnStrategy;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }
}
