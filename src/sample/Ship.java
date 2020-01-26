package sample;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private int size;
    private int health;
    private List<Tile> claimedTiles;
    private final ShipType type;

    public Ship(ShipType type) {
        this.type = type;

        switch (type){
            case CARRIER:
                this.size = 5;
                break;
            case BATTLESHIP:
                this.size = 4;
                break;
            case CRUISER:
                this.size = 3;
                break;
            case SUBMARINE:
                this.size = 3;
                break;
            case DESTROYER:
                this.size = 2;
                break;
        }

        this.health = size;
        claimedTiles = new ArrayList<>();
    }

    public Boolean checkIfTileIsSuitable(Tile tile){

        if(tile.getRow() == 0 || tile.getColumn() == 0)
            return false;

        if(claimedTiles.size() == this.size)
            return false;

        if(claimedTiles.size() == 0 )
            return true;
        else if(claimedTiles.size() == 1){

            Tile first = claimedTiles.get(0);

            if( Math.abs( first.getRow() - tile.getRow()) == 1 || Math.abs( first.getColumn() - tile.getColumn()) == 1) {
                if ( first.getColumn() == tile.getColumn() || first.getRow() == tile.getRow()){
                    return true;
                }
            }
        }
        else {

            Tile first = claimedTiles.get(0);
            Tile second = claimedTiles.get(1);

            Boolean columnOriented = false;
            Boolean rowOriented = false;

            if(first.getColumn() == second.getColumn()) {
                columnOriented = true;
            }
            else if (first.getRow() == second.getRow()) {
                rowOriented = true;
            }

            if(columnOriented) {
                for (Tile t : claimedTiles) {
                    if ( tile.getColumn() == first.getColumn() & Math.abs(t.getRow() - tile.getRow()) == 1 )
                        return true;
                }
            }
            else if(rowOriented){
                for (Tile t : claimedTiles) {
                    if (tile.getRow() == first.getRow() & Math.abs(t.getColumn() - tile.getColumn()) == 1 )
                        return true;
                }
            }
        }

        return  false;
    }

    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public List<Tile> getClaimedTiles() {
        return claimedTiles;
    }

    public ShipType getType() {
        return type;
    }
}
