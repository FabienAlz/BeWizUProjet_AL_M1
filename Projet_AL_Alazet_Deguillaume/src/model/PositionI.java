package model;

import java.io.Serializable;

public interface PositionI extends Serializable {

    double getX();

    double getY();

    void setX(double x);

    void setY(double y);

}

