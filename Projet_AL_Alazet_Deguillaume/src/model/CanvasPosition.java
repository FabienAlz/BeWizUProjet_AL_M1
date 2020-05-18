package model;

import java.io.Serializable;

public class CanvasPosition implements PositionI, Serializable {
    private static final long serialVersionUID = 7072556856740817626L;

    private double x, y;

    public CanvasPosition(double x, double y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException("Position out of bonds");
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}
