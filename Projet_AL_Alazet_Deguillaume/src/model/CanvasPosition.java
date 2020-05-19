package model;

import java.io.Serializable;

public class CanvasPosition implements PositionI, Serializable {
    private static final long serialVersionUID = 7072556856740817626L;

    private double x, y;

    public CanvasPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /******************************
     *          GETTERS           *
     ******************************/
    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}
