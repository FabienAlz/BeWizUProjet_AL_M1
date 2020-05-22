package model;

import java.io.Serializable;

public class Position implements Serializable, PositionI {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public double getX() {
        return this.x;
    }


    public double getY() {
        return this.y;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Computes the distance between p and this
     *
     * @param p the position to compute the distance to
     * @return the distance
     */
    public double distance(Position p) {
        return Math.sqrt(Math.pow(p.getX() - this.x, 2) + Math.pow(p.getY() - this.y, 2));
    }
}
