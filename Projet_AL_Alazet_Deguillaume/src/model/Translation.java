package model;

import java.io.Serializable;

public class Translation implements Serializable, PositionI  {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;

    public Translation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
