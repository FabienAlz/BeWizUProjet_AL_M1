package model;

public class CanvasPosition implements PositionI {

    private double x, y;

    public CanvasPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
