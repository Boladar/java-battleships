package sample;

public class Tile {


    private final int row;
    private final int column;
    private boolean isHit;
    private boolean isOccupied;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        isHit = false;
        isOccupied = false;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
