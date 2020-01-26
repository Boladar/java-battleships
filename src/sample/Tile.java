package sample;

public class Tile {

    private boolean containsShip;
    private boolean isHit;

    public Tile() {
        containsShip = false;
        isHit = false;
    }

    public Tile(boolean containsShip, boolean isHit) {
        this.containsShip = containsShip;
        this.isHit = isHit;
    }

    public boolean isContainsShip() {
        return containsShip;
    }

    public void setContainsShip(boolean containsShip) {
        this.containsShip = containsShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
