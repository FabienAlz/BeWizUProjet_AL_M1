package model;

public class CanvasPosition implements PositionI {

    private int x, y;

    public CanvasPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
