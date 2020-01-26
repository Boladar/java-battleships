package sample;

public class Tile {


    private final int row;
    private final int column;
    private boolean isHit;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        isHit = false;
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
}
