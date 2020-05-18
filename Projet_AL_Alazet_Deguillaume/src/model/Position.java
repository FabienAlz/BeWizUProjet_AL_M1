package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Position implements PositionI, Serializable {

    private double x;


    private double y;

    public Position(double x, double y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException("Position out of bonds");
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

    public double distance(Position p) {
        return Math.sqrt(Math.pow(p.getX() - this.x, 2) + Math.pow(p.getY() - this.y, 2));
    }
}
